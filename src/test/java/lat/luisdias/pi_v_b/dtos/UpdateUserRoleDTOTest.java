package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lat.luisdias.pi_v_b.entities.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateUserRoleDTOTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void devePassarQuandoRoleForValido() {
        UpdateUserRoleDTO dto = new UpdateUserRoleDTO();
        dto.setRole(UserRole.USER);
        Set<ConstraintViolation<UpdateUserRoleDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Esperado nenhum erro de validação");
    }

    @Test
    void deveFalharQuandoRoleForInvalido() {
        UpdateUserRoleDTO dto = new UpdateUserRoleDTO();
        dto.setRole(null);
        Set<ConstraintViolation<UpdateUserRoleDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Campo obrigatório")));
    }
}
