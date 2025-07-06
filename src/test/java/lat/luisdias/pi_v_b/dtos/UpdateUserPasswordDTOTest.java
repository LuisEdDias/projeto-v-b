package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateUserPasswordDTOTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void devePassarQuandoSenhaForValida() {
        UpdateUserPasswordDTO dto = new UpdateUserPasswordDTO();
        dto.setPassword("Senha123");
        dto.setConfirmPassword("Senha123");
        Set<ConstraintViolation<UpdateUserPasswordDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Esperado nenhum erro de validação");
    }

    @Test
    void deveFalharQuandoSenhasForemDiferentes() {
        UpdateUserPasswordDTO dto = new UpdateUserPasswordDTO();
        dto.setPassword("Senha123");
        dto.setConfirmPassword("Diferente123");
        Set<ConstraintViolation<UpdateUserPasswordDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("senhas não coincidem")));
    }
}
