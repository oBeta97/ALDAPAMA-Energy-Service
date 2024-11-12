package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.IndirizzoDTO;
import gruppo4.ALDAPAMA.entities.Cliente;
import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.entities.Indizzo;
import gruppo4.ALDAPAMA.enums.TipoSede;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
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

    public Indizzo saveIndirizzo(IndirizzoDTO body) {
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
        Indizzo newIndirizzo = new Indizzo(body.cap(), body.civico(),
                clienteFound, comuneFound, tipoSede, body.via());
        return this.indirizzoRepo.save(newIndirizzo);
    }

    public Page<Indizzo> findAllIndirizzi(int page, int size) {
        if (size > 30) size = 30;
        Pageable pageable = PageRequest.of(page, size);
        return indirizzoRepo.findAll(pageable);
    }


}
