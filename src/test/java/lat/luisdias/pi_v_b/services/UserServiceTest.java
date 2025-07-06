package lat.luisdias.pi_v_b.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lat.luisdias.pi_v_b.dtos.CreateUserDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserEmailDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserPasswordDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserRoleDTO;
import lat.luisdias.pi_v_b.entities.User;
import lat.luisdias.pi_v_b.entities.UserRole;
import lat.luisdias.pi_v_b.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarTodosOsUsuáriosCadastrados() {
        when(userRepository.findAll()).thenReturn(List.of(new User("A", "B", "a@b.com", "abc123", LocalDate.now(), UserRole.USER)));
        var result = userService.getAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void deveCadastrarUmUsuarioComDadosValidos() {
        CreateUserDTO dto = getValidDTO();
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        userService.create(dto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void deveFalharSeEmailJaExistirNoSistema() {
        CreateUserDTO dto = getValidDTO();
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(mock(User.class)));

        assertThatThrownBy(() -> userService.create(dto))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Já existe um usuário cadastrado com este email");
    }

    @Test
    void deveAtualizarUmUsuarioComEmailValido() {
        Long id = 1L;
        UpdateUserEmailDTO dto = new UpdateUserEmailDTO();
        dto.setEmail("new@email.com");
        User user = mock(User.class);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        userService.updateEmail(id, dto);

        verify(user).update(eq(dto.getEmail()), any(), any());
    }

    @Test
    void deveFalharAoAtualizarSeEmailJaExistir() {
        Long id = 1L;
        UpdateUserEmailDTO dto = new UpdateUserEmailDTO();
        dto.setEmail("duplicate@email.com");

        User currentUser = mock(User.class);
        when(currentUser.getId()).thenReturn(id);

        User otherUser = mock(User.class);
        when(otherUser.getId()).thenReturn(2L);

        when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(otherUser));

        assertThatThrownBy(() -> userService.updateEmail(id, dto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Já existe um usuário cadastrado com este email");
    }

    @Test
    void deveAtualizarPerfilDeUsuarioValido() {
        Long id = 1L;
        UpdateUserRoleDTO dto = new UpdateUserRoleDTO();
        dto.setRole(UserRole.ADMIN);
        User user = mock(User.class);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.updateRole(id, dto);

        verify(user).update(any(), eq(UserRole.ADMIN), any());
    }

    @Test
    void deveFalharAoAtualizarPerfilSeUsuarioInvalido() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UpdateUserRoleDTO dto = new UpdateUserRoleDTO();
        dto.setRole(UserRole.ADMIN);
        assertThatThrownBy(() -> userService.updateRole(1L, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deveAtualizarSenhaDeUsuarioValido() {
        Long id = 1L;
        UpdateUserPasswordDTO dto = new UpdateUserPasswordDTO();
        dto.setPassword("newpass123");
        User user = mock(User.class);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.updatePassword(id, dto);

        String encoded = Base64.getEncoder().encodeToString(dto.getPassword().getBytes());
        verify(user).update(any(), any(), eq(encoded));
    }

    @Test
    void deveFalharAoAtualizarSenhaDeUsuarioInvalido() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UpdateUserPasswordDTO dto = new UpdateUserPasswordDTO();
        dto.setPassword("12345678");
        assertThatThrownBy(() -> userService.updatePassword(1L, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deveRemoverUsuarioValido() {
        User user = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void deveFalharAoRemoverUsuarioInvalido() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.delete(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // Método auxiliar para criar DTO válido
    private CreateUserDTO getValidDTO() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setFirstName("Luís");
        dto.setLastName("Dias");
        dto.setEmail("luis@example.com");
        dto.setPassword("Senha123");
        dto.setConfirmPassword("Senha123");
        dto.setBirthDate(LocalDate.now().minusYears(30));
        dto.setRole(UserRole.ADMIN);
        return dto;
    }
}
