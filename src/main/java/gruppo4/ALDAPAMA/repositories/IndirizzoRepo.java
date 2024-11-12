package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Indizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndirizzoRepo extends JpaRepository<Indizzo,Long> {
    boolean existsByViaAndCivicoAndCAPAndComune_Id(String via,
                                                   long civico,
                                                   String CAP,
                                                   long id_comune);
}
