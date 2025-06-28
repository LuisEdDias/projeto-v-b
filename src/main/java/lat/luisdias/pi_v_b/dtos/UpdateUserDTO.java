package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lat.luisdias.pi_v_b.entities.UserRole;

// DTO usado para receber os dados de atualização do usuário com validações iniciais
public record UpdateUserDTO(
        @NotNull(message = "Campo obrigatório")
        @Email(message = "O email precisa ter um formato válido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[a-zA-Z])([0-9a-zA-Z#@$*&?-]){8,}$",
                message = "Deve ter no mínimo 8 caracteres, contendo ao menos letra e números"
        )
        String password,
        @NotNull(message = "Campo obrigatório")
        UserRole role
) {
}
