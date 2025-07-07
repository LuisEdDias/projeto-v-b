package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.constraints.*;

// DTO usado para receber os dados de atualização do email do usuário
public class UpdateUserEmailDTO {

        @NotBlank(message = "Campo obrigatório")
        @Email(message = "O email precisa ter um formato válido")
        private String email;

        // === Getters e Setters ===

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }
}
