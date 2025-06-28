package lat.luisdias.pi_v_b.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lat.luisdias.pi_v_b.dtos.CreateUserDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserDTO;

import java.time.LocalDate;
import java.util.Base64;

// Classe da entidade que representa um usuário do sistema
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private UserRole role;

    // Construtor padrão exigido pela JPA
    protected User() {}

    // Contrutor da classe que recebe um DTO sanitizado
    public User(CreateUserDTO userDTO) {
        this.firstName = userDTO.firstName();
        this.lastName = userDTO.lastName();
        this.email = userDTO.email();
        this.password = passwordEncode(userDTO.password());
        this.birthDate = userDTO.birthDate();
        this.role = userDTO.role();
    }

    // Método da entidade para atualização de dados
    public void update(UpdateUserDTO userDTO) {
        this.email = userDTO.email();
        this.password = passwordEncode(userDTO.password());
        this.role = userDTO.role();
    }

    // Exemplo de método auxiliar para codificação da senha antes
    // de persistir no banco
    private String passwordEncode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserRole getRole() {
        return role;
    }
}
