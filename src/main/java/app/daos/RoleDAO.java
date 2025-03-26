package app.daos;

import app.entities.Roles;
import app.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleDAO extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RoleType name);
}
