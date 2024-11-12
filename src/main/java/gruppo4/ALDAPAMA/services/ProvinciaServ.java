package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
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
        this.checkNomeOrSigla(body);
        Provincia newProvincia = new Provincia(body.nome(), body.sigla());
        return this.provinciaRepo.save(newProvincia);
    }

    private void checkNomeOrSigla(ProvinciaDTO body) {
        this.provinciaRepo.findByNomeOrSigla(body.nome(), body.sigla())
                .ifPresent(
                        provincia -> {
                            throw new BadRequestException("Provincia " + body.nome() + " è già esistente");
                        }
                );
    }

    public Page<Provincia> findAllProvince(int page, int size) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return this.provinciaRepo.findAll(pageable);
    }

    public Provincia findProvinciaById(long id_provincia) {
        return this.provinciaRepo.findById(id_provincia).orElseThrow(() ->
                new NotFoundException("Provoncia con questo id non trovato"));
    }

    public Provincia findProvinciaByIdAndUp(long id_provincia, ProvinciaDTO body) {
        Provincia provFound = this.findProvinciaById(id_provincia);
        if (!provFound.getNome().equals(body.nome()) || !provFound.getSigla().equals(body.sigla())) {
            this.checkNomeOrSigla(body);
        }
        provFound.setNome(body.nome());
        provFound.setSigla(body.sigla());
        return this.provinciaRepo.save(provFound);
    }

    public void findProvinciaByIdAndDel(long id_provincia) {
        Provincia provToDel = this.findProvinciaById(id_provincia);
        this.provinciaRepo.delete(provToDel);
    }
}
