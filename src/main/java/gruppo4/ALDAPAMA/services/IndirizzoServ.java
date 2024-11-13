package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.IndirizzoDTO;
import gruppo4.ALDAPAMA.entities.Cliente;
import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.entities.Indirizzo;
import gruppo4.ALDAPAMA.enums.TipoSede;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.IndirizzoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IndirizzoServ {
    @Autowired
    private IndirizzoRepo indirizzoRepo;
    @Autowired
    private ComuneServ comuneServ;
    @Autowired
    private ClienteService clienteService;

    public Indirizzo saveIndirizzo(IndirizzoDTO body) {
        Comune comuneFound = this.comuneServ.findComuneById(body.id_comune());
        Cliente clienteFound = this.clienteService.getById(body.id_cliente());
        TipoSede tipoSede = TipoSede.stringToEnum(body.tipoSede());
        if (indirizzoRepo.existsByViaAndCivicoAndCAPAndTipoSedeAndComune_Id(body.via(),
                body.civico(),
                body.cap(),
                tipoSede,
                body.id_comune())) {
            throw new BadRequestException("Esiste già questo indirizzo in questo comune");
        }
        Indirizzo newIndirizzo = new Indirizzo(body.cap(), body.civico(),
                clienteFound, comuneFound, tipoSede, body.via());
        return this.indirizzoRepo.save(newIndirizzo);
    }

    public Page<Indirizzo> findAllIndirizzi(int page, int size) {
        if (size > 30) size = 30;
        Pageable pageable = PageRequest.of(page, size);
        return indirizzoRepo.findAll(pageable);
    }

    public Indirizzo findIndirizzoById(long id_indirizzo) {
        return this.indirizzoRepo.findById(id_indirizzo).orElseThrow(() ->
                new NotFoundException("Indirizzo con questo id non trovato"));
    }

    public Indirizzo findIndirizzoByIdAndUp(long id_indirizzo, IndirizzoDTO body) {
        Indirizzo indirizzoFound = this.findIndirizzoById(id_indirizzo);
        Comune comuneToUp = this.comuneServ.findComuneById(body.id_comune());
        Cliente clienteToUp = this.clienteService.getById(body.id_cliente());
        TipoSede tipoSedeToUp = TipoSede.stringToEnum(body.tipoSede());
        if (indirizzoRepo.existsByViaAndCivicoAndCAPAndTipoSedeAndComune_Id(body.via(),
                body.civico(),
                body.cap(),
                tipoSedeToUp,
                body.id_comune())) {
            throw new BadRequestException("Esiste già questo indirizzo in questo comune");
        }
        Indirizzo indirizzoToUp = new Indirizzo(body.cap(), body.civico(), clienteToUp,comuneToUp,tipoSedeToUp, body.via());
        return this.indirizzoRepo.save(indirizzoToUp);
    }

    public void findIndizzoByIdAndDel(long id_indirizzo) {
        Indirizzo indirizzoToDel = this.findIndirizzoById(id_indirizzo);
        this.indirizzoRepo.delete(indirizzoToDel);
    }

}
