package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.constraints.*;
import lat.luisdias.pi_v_b.entities.UserRole;

import java.time.LocalDate;
import java.time.Period;

// DTO usado para receber os dados de criação do usuário com validações iniciais
public class CreateUserDTO {

        @NotBlank(message = "Campo obrigatório")
        @Pattern(
                regexp = "^[a-zA-Z]+(\\s?[a-zA-Z]+)*$",
                message = "Use apenas letras e espaços simples"
        )
        private String firstName;

        @NotBlank(message = "Campo obrigatório")
        private String lastName;

        @NotBlank(message = "Campo obrigatório")
        @Email(message = "O email precisa ter um formato válido")
        private String email;

        @NotBlank(message = "Campo obrigatório")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[a-zA-Z])([0-9a-zA-Z#@$*&?-]){8,}$",
                message = "Deve ter no mínimo 8 caracteres, contendo ao menos letras e números"
        )
        private String password;

        @NotBlank(message = "Campo obrigatório")
        private String confirmPassword;

        @NotNull(message = "Campo obrigatório")
        private LocalDate birthDate;

        @NotNull(message = "Campo obrigatório")
        private UserRole role;

        // === Validação de idade ===
        @AssertTrue(message = "O usuário deve ser maior de idade")
        public boolean isOfLegalAge() {
                return birthDate != null && Period.between(birthDate, LocalDate.now()).getYears() >= 18;
        }

        // === Validação de senha ===
        @AssertTrue(message = "As senhas não coincidem")
        public boolean isPasswordConfirmed() {
                return password != null && password.equals(confirmPassword);
        }

        // === Getters e Setters ===

        public String getFirstName() { return firstName; }

        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }

        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }

        public void setPassword(String password) { this.password = password; }

        public String getConfirmPassword() { return confirmPassword; }

        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

        public LocalDate getBirthDate() { return birthDate; }

        public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

        public UserRole getRole() { return role; }

        public void setRole(UserRole role) { this.role = role; }
}
