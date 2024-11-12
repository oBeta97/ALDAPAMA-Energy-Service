package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, Long> {


    Optional<Utente> findByUsername(String username);

    Optional<Utente> findByEmail(String Email);



}
