package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Indirizzo;
import gruppo4.ALDAPAMA.enums.TipoSede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndirizzoRepo extends JpaRepository<Indirizzo,Long> {
    boolean existsByViaAndCivicoAndCAPAndTipoSedeAndComune_Id(String via,
                                                   long civico,
                                                   String CAP,
                                                   TipoSede tipoSede,
                                                   long id_comune);
}
