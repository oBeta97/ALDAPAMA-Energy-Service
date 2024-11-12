package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.ComuneDTO;
import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.repositories.ComuneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComuneServ {
    @Autowired
    ProvinciaServ provinciaServ;
    @Autowired
    private ComuneRepo comuneRepo;

    private void checkNomeAndIdProvincia(ComuneDTO body) {
        this.comuneRepo.findByNomeOrProvincia_Id(body.nome(), body.id_provincia())
                .ifPresent(
                        comuine -> {
                            throw new BadRequestException("Comune: " + body.nome() +
                                    " e con id della provincia: " + body.id_provincia() + " è già esistente");
                        }
                );
    }

    public Comune saveComune(ComuneDTO body) {
        Provincia provFound = provinciaServ.findProvinciaById(body.id_provincia());
        this.checkNomeAndIdProvincia(body);
        Comune newComune = new Comune(body.nome(), provFound);
        return this.comuneRepo.save(newComune);
    }

    public Page<Comune> findAllComuni(int page,int size){
        if (size>30) size= 30;
        Pageable pageable = PageRequest.of(page, size);
        return this.comuneRepo.findAll(pageable);
    }

}
