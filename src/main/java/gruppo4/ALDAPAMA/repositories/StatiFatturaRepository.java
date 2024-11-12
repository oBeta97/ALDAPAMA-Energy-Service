package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Fattura;
import gruppo4.ALDAPAMA.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StatiFatturaRepository extends JpaRepository<StatoFattura, Long> {

    Optional<StatoFattura> findByDescrizione (String descrizione);

}
