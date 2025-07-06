package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.constraints.*;

// DTO usado para receber os dados de atualização da sennha do usuário
public class UpdateUserPasswordDTO {

        @NotBlank(message = "Campo obrigatório")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[a-zA-Z])([0-9a-zA-Z#@$*&?-]){8,}$",
                message = "Deve ter no mínimo 8 caracteres, contendo ao menos letras e números"
        )
        private String password;

        @NotBlank(message = "Campo obrigatório")
        private String confirmPassword;

        // === Validação de senha ===
        @AssertTrue(message = "As senhas não coincidem")
        public boolean isPasswordConfirmed() {
                return password != null && password.equals(confirmPassword);
        }

        // === Getters e Setters ===

        public String getPassword() { return password; }

        public void setPassword(String password) { this.password = password; }

        public String getConfirmPassword() { return confirmPassword; }

        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
