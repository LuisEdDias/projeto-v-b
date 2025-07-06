package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lat.luisdias.pi_v_b.entities.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUserDTOTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveSerValidoQuandoTodosOsCamposForemCorretos() {
        CreateUserDTO dto = getValidDTO();
        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        violations.forEach(System.out::println);
        assertTrue(violations.isEmpty(), "Esperado nenhum erro de validação");
    }

    @Test
    void deveFalharSeMenorDeIdade() {
        CreateUserDTO dto = getValidDTO();
        dto.setBirthDate(LocalDate.now().minusYears(17));
        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("maior de idade")));
    }

    @Test
    void deveFalharSeSenhaNaoConfirmada() {
        CreateUserDTO dto = getValidDTO();
        dto.setConfirmPassword("diferente123");
        Set<ConstraintViolation<CreateUserDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("senhas não coincidem")));
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
