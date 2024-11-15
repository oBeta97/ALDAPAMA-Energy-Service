package gruppo4.ALDAPAMA.repositories;

import gruppo4.ALDAPAMA.entities.Contatto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContattoRepository extends JpaRepository<Contatto, Long> {
    Optional<Contatto> findByEmail(String email);
}