package lat.luisdias.pi_v_b.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lat.luisdias.pi_v_b.dtos.CreateUserDTO;
import lat.luisdias.pi_v_b.dtos.GetUserDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserDTO;
import lat.luisdias.pi_v_b.entities.User;
import lat.luisdias.pi_v_b.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Classe de serviço com as regras de negócio
@Service
public class UserService {
    // Declaração da dependência do repositório de usuários
    private final UserRepository userRepository;

    // Injeção de dependência via construtor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método que busca todos os usuários salvos no banco
    public List<GetUserDTO> getAll() {
        // Retorna uma lista de GetUserDTO
        return userRepository.findAll().stream().map(GetUserDTO::new).toList();
    }

    // Valida os dados de entrada e salva um novo usuário no banco
    @Transactional
    public void create(CreateUserDTO userDTO) {
        // Verifica se já existe um cadastro com o email fornecido
        if (validateEmail(userDTO.email()).isPresent()) {
            throw new ValidationException("Já existe um usuário cadastrado com este email");
        }

        // Encriptação da senha
        String encodedPassword = passwordEncrypt(userDTO.password());

        // Criação do objeto usuário
        User user = new User(
                userDTO.firstName(),
                userDTO.lastName(),
                userDTO.email(),
                encodedPassword,
                userDTO.birthDate(),
                userDTO.role()
        );

        // Chamada do repositório para salvar o usuário no banco de dados
        userRepository.save(user);
    }

    // Valida os dados de entrada e atualiza as informações do usuário
    @Transactional
    public void update(Long id, UpdateUserDTO userDTO) {
        // Recupera o usuário no banco de dados e lança exceção caso não encontre
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        // Verifica se existe outro usuário com o email fornecido
        Optional<User> userAux = this.validateEmail(userDTO.email());
        if (userAux.isPresent() && !Objects.equals(userAux.get().getId(), id)) {
            throw new ValidationException("Já existe um usuário cadastrado com este email");
        }

        // Encriptação da senha
        String encodedPassword = passwordEncrypt(userDTO.password());

        // Atualiza os dados no objeto usuário
        user.get().update(userDTO.email(), userDTO.role(), encodedPassword);
    }

    // Deleta um usuário do banco de dados, caso encontrado
    @Transactional
    public void delete(Long id) {
        // Recupera o usuário no banco de dados e lança exceção caso não encontre
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        // Apaga o registro do banco de dados
        userRepository.delete(user.get());
    }

    // Método auxiliar para validação do email
    private Optional<User> validateEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Exemplo de método auxiliar para codificação da senha antes de persistir no banco
    private String passwordEncrypt(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
