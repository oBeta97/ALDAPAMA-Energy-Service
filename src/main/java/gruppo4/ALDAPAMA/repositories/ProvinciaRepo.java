package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepo extends JpaRepository<Provincia,Long> {
}
