package lat.luisdias.pi_v_b.repositories;

import lat.luisdias.pi_v_b.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
