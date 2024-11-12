package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.StatoFatturaDTO;
import gruppo4.ALDAPAMA.entities.StatoFattura;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.StatiFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StatiFatturaService {

    @Autowired
    private StatiFatturaRepository statiFatturaRepo;

    private void checkStatoFattura(StatoFatturaDTO newStatoFattura){
        this.statiFatturaRepo.findByDescrizione(newStatoFattura.descrizione()).
                ifPresent(utente -> {
                    throw new BadRequestException("La StatoFattura con descrizione " + newStatoFattura.descrizione() + " esiste già");
                });
    }

    public Page<StatoFattura> getAll(int page, int size, String sortBy){
        if (size > 25) size = 25;

        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return this.statiFatturaRepo.findAll(pageable);
    }

    public StatoFattura getById (long statoId){
        return this.statiFatturaRepo.findById(statoId).orElseThrow(() -> new NotFoundException("StatoFattura con id " + statoId + " non trovato!"));
    }

    public StatoFattura saveNewStatoFattura (StatoFatturaDTO newStatoFattura){

        this.checkStatoFattura(newStatoFattura);
        StatoFattura newFattura = new StatoFattura(newStatoFattura.descrizione());

        return this.statiFatturaRepo.save(newFattura);
    }

    public StatoFattura updateUser(long idToUpdate, StatoFatturaDTO newStatoFattura){
        StatoFattura found = this.getById(idToUpdate);
        this.checkStatoFattura(newStatoFattura);

        found.setDescrizione(newStatoFattura.descrizione());
        return this.statiFatturaRepo.save(found);
    }

    public void deleteFattura (long idToDelete){
        this.statiFatturaRepo.delete(
                this.getById(idToDelete)
        );
    }


}
