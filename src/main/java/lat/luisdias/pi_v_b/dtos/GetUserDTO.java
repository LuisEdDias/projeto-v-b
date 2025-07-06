package lat.luisdias.pi_v_b.dtos;

import lat.luisdias.pi_v_b.entities.User;

import java.time.format.DateTimeFormatter;

public record GetUserDTO(
        Long id,
        String fullName,
        String email,
        String birthDate,
        String role
) {
    public GetUserDTO(User user){
        this(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                user.getRole().label
        );
    }
}
