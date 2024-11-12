package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FattureRepository extends JpaRepository<Fattura, Long> {

    Optional<Fattura> findByNumFatt (String numeroFattura);

}
