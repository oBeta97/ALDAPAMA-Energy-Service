package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepo extends JpaRepository<Provincia,Long> {
    Optional<Provincia> findByNomeOrSigla(String nome,String sigla);
}
