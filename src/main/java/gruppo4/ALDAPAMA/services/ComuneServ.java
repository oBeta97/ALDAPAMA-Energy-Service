package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.ComuneDTO;
import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.ComuneRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ComuneServ {
    @Autowired
    ProvinciaServ provinciaServ;
    @Autowired
    private ComuneRepo comuneRepo;


    public Comune saveComune(ComuneDTO body) {
        Provincia provFound = provinciaServ.findProvinciaById(body.id_provincia());
        if (comuneRepo.existsByNomeAndProvincia_id(body.nome(), provFound.getId())) {
            throw new BadRequestException("Esiste già un comune con quel nome in quella provincia");
        }
        Comune newComune = new Comune(body.nome(), provFound);
        return this.comuneRepo.save(newComune);
    }

    @Transactional
    public List<Comune> saveComuneList(List<ComuneDTO> comuneDTOList){

        List<Comune> res = new ArrayList<>();

        for (ComuneDTO comuneDTO : comuneDTOList){
            res.add(this.saveComune(comuneDTO));
        }

        return res;
    }


    public Page<Comune> findAllComuni(int page, int size) {
        if (size > 30) size = 30;
        Pageable pageable = PageRequest.of(page, size);
        return this.comuneRepo.findAll(pageable);
    }

    public Comune findComuneById(long id_comune) {
        return this.comuneRepo.findById(id_comune).orElseThrow(() ->
                new NotFoundException("Comune con questo id non trovato"));
    }

    public Comune findComuneByIdAndUp(long id_comune, ComuneDTO body) {
        Comune comuneFound = this.findComuneById(id_comune);
        Provincia provFound = this.provinciaServ.findProvinciaById(body.id_provincia());
        if (comuneRepo.existsByNomeAndProvincia_id(body.nome(), provFound.getId())) {
            throw new BadRequestException("Esiste già un comune con quel nome in quella provincia");
        }
        comuneFound.setNome(body.nome());
        comuneFound.setProvincia(provFound);
        return comuneFound;
    }

    public void findComuneByIdAndDel(long id_comune) {
        Comune comuneToDel = this.findComuneById(id_comune);
        this.comuneRepo.delete(comuneToDel);
    }


}
