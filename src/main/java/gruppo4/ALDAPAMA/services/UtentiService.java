package gruppo4.ALDAPAMA.services;


import gruppo4.ALDAPAMA.entities.Utente;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UtentiService {

    @Autowired
    private UtentiRepository utentiRepo;

    public Page<Utente> getAll(int page, int size, String sortBy){
        if (size > 25) size = 25;

        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return this.utentiRepo.findAll(pageable);
    }

    public Utente getById (long userId){
        return this.utentiRepo.findById(userId).orElseThrow(() -> new NotFoundException("Utente con id " + userId + " non trovato!"));
    }

//    public Utente saveNewUtente (){}

}
