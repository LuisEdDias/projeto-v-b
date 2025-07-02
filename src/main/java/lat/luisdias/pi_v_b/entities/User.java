package lat.luisdias.pi_v_b.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

// Classe da entidade que representa um usuário do sistema
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate birthDate;
    private UserRole role;

    // Construtor padrão exigido pela JPA
    protected User() {}

    // Contrutor da classe que recebe os dados
    public User(
            String firstName,
            String lastName,
            String email,
            String password,
            LocalDate birthDate,
            UserRole role
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
    }

    // Método da entidade para atualização de dados
    public void update(String email, UserRole role, String password) {
        this.email = email;
        this.password = password;
        this.role = role;
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
