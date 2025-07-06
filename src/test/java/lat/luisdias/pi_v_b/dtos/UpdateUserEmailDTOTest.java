package lat.luisdias.pi_v_b.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateUserEmailDTOTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void devePassarQuandoEmailForValido() {
        UpdateUserEmailDTO dto = new UpdateUserEmailDTO();
        dto.setEmail("email@example.com");
        Set<ConstraintViolation<UpdateUserEmailDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Esperado nenhum erro de validação");
    }

    @Test
    void deveFalharQuandoEmailForInvalido() {
        UpdateUserEmailDTO dto = new UpdateUserEmailDTO();
        dto.setEmail("email.example.com");
        Set<ConstraintViolation<UpdateUserEmailDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("precisa ter um formato válido")));
    }
}
