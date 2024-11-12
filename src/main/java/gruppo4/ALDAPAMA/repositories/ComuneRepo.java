package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComuneRepo extends JpaRepository<Comune,Long> {
    Optional<Comune> findByNomeOrProvincia_Id(String nome, long id_provincia);
    boolean existsByNomeAndProvincia(String nome, Provincia provincia);
}
