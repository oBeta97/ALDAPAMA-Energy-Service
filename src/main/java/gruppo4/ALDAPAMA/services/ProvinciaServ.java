package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.ProvinciaRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinciaServ {
    @Autowired
    private ProvinciaRepo provinciaRepo;

    public Provincia saveProvincia(ProvinciaDTO body) {
        this.checkNomeAndSigla(body);
        Provincia newProvincia = new Provincia(body.nome(), body.sigla());
        return this.provinciaRepo.save(newProvincia);
    }

    @Transactional
    public List<Provincia> saveProvinciaList(List<ProvinciaDTO> provinciaDTOList){

        List<Provincia> res = new ArrayList<>();

        for (ProvinciaDTO provinciaDTO : provinciaDTOList){
            res.add(this.saveProvincia(provinciaDTO));
        }

        return res;
    }


    private void checkNomeAndSigla(ProvinciaDTO body) {
        this.provinciaRepo.findByNomeAndSigla(body.nome(), body.sigla())
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
                new NotFoundException("Provincia con id " + id_provincia + " non trovato"));
    }

    public Provincia findProvinciaByIdAndUp(long id_provincia, ProvinciaDTO body) {
        Provincia provFound = this.findProvinciaById(id_provincia);
        if (!provFound.getNome().equals(body.nome()) || !provFound.getSigla().equals(body.sigla())) {
            this.checkNomeAndSigla(body);
        }
        provFound.setNome(body.nome());
        provFound.setSigla(body.sigla());
        return this.provinciaRepo.save(provFound);
    }

    public void findProvinciaByIdAndDel(long id_provincia) {
        Provincia provToDel = this.findProvinciaById(id_provincia);
        this.provinciaRepo.delete(provToDel);
    }

    public Provincia findProvinciaByNome (String nomeProvincia){
        return this.provinciaRepo.findByNome(nomeProvincia)
                .orElseThrow(() -> new NotFoundException("La provincia " + nomeProvincia + " non trovata"));
    }
}
