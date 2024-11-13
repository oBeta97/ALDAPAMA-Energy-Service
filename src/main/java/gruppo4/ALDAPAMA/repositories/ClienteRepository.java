package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByPartitaIva(String partitaIva);
    boolean existsByPartitaIva(String partitaIva);
    boolean existsByPec(String pec);
}
