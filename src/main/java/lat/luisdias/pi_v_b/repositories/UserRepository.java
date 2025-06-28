package lat.luisdias.pi_v_b.repositories;

import lat.luisdias.pi_v_b.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
