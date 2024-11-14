package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByPartitaIva(String partitaIva);

    boolean existsByPartitaIva(String partitaIva);

    @Query("SELECT c FROM Cliente c " +
            "JOIN Contatto co ON co.cliente.id = c.id " +
            "ORDER BY co.nome")
    List<Cliente> findAllOrderByContattoNome();

    @Query("SELECT c FROM Cliente c " +
            "JOIN Fattura f ON f.cliente.id = c.id " +
            "WHERE FUNCTION('YEAR', f.data) = :anno " +
            "GROUP BY c " +
            "ORDER BY SUM(f.importo) ")
    List<Cliente> findAllOrderByFatturatoAnnuale(int anno);

    @Query("SELECT c FROM Cliente c " +
            "ORDER BY c.dataInserimento DESC")
    List<Cliente> findAllOrderByDataInserimento();

    @Query("SELECT c FROM Cliente c " +
            "ORDER BY c.dataUltimoContatto DESC NULLS LAST")
    List<Cliente> findAllOrderByDataUltimoContatto();


}
