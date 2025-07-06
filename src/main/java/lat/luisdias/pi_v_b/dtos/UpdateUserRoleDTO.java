package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.constraints.*;
import lat.luisdias.pi_v_b.entities.UserRole;

// DTO usado para receber os dados de atualização do perfil de usuário
public class UpdateUserRoleDTO {

        @NotNull(message = "Campo obrigatório")
        private UserRole role;

        // === Getters e Setters ===

        public UserRole getRole() { return role; }

        public void setRole(UserRole role) { this.role = role; }
}
