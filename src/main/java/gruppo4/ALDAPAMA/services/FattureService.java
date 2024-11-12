package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.FatturaDTO;
import gruppo4.ALDAPAMA.entities.Cliente;
import gruppo4.ALDAPAMA.entities.Fattura;
import gruppo4.ALDAPAMA.entities.StatoFattura;
import gruppo4.ALDAPAMA.entities.Utente;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.FattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FattureService {

    @Autowired
    private FattureRepository fattureRepo;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private ClienteService clienteService;


    private void checkFattura (FatturaDTO newFatturaDTO){
        this.fattureRepo.findByNumFatt(newFatturaDTO.numFatt()).
                ifPresent(utente -> {
                    throw new BadRequestException("La fattura con codice " + newFatturaDTO.numFatt() + " esiste già");
                });
    }

    public Page<Fattura> getAll(int page, int size, String sortBy){
        if (size > 25) size = 25;

        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return this.fattureRepo.findAll(pageable);
    }

    public Fattura getById (long userId){
        return this.fattureRepo.findById(userId).orElseThrow(() -> new NotFoundException("Fattura con id " + userId + " non trovato!"));
    }

    public Fattura saveNewFattura (FatturaDTO newFatturaDTO){
        Cliente clienteFattura = this.clienteService.getById(newFatturaDTO.idCliente());
        Utente utenteFattura = this.utentiService.getById(newFatturaDTO.idUtente());
//        TODO - Da aggiungere la parte dello stato
        StatoFattura statoFattura = new StatoFattura();

        this.checkFattura(newFatturaDTO);
        Fattura newFattura = new Fattura(newFatturaDTO, utenteFattura, clienteFattura,statoFattura);

        return this.fattureRepo.save(newFattura);
    }

    public Fattura updateUser(long idToUpdate, FatturaDTO newFatturaDTO){
        Fattura found = this.getById(idToUpdate);
        Cliente clienteFattura = this.clienteService.getById(newFatturaDTO.idCliente());
        Utente utenteFattura = this.utentiService.getById(newFatturaDTO.idUtente());
//        TODO - Da aggiungere la parte dello stato
        StatoFattura statoFattura = new StatoFattura();

        this.checkFattura(newFatturaDTO);

        found.setCliente(clienteFattura);
        found.setData(newFatturaDTO.data());
        found.setStato(statoFattura);
        found.setNumFatt(newFatturaDTO.numFatt());
        found.setImporto(newFatturaDTO.importo());

        return this.fattureRepo.save(found);
    }

    public void deleteFattura (long idToDelete){
        this.fattureRepo.delete(
                this.getById(idToDelete)
        );
    }


}
