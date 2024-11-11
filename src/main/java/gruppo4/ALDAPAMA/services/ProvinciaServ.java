package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.repositories.ProvinciaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaServ {
    @Autowired
    private ProvinciaRepo provinciaRepo;

    public Provincia saveProvincia(ProvinciaDTO body) {
        this.provinciaRepo.findByNomeOrSigla(body.nome(), body.sigla())
                .ifPresent(
                        provincia -> {
                            throw new BadRequestException("Provincia " + body.nome() + " è già esistente");
                        }
                );
        Provincia newProvincia = new Provincia(body.nome(), body.sigla());
        return this.provinciaRepo.save(newProvincia);
    }

    public Page<Provincia> findAllProvince(int page, int size) {
        if (size > 10) size = 10;
        Pageable pageable= PageRequest.of(page, size);
        return this.provinciaRepo.findAll(pageable);
    }
}
