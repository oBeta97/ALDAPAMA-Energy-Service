package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, Long> {
}
