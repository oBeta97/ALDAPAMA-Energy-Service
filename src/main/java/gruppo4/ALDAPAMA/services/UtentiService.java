package gruppo4.ALDAPAMA.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gruppo4.ALDAPAMA.dto.NewUtenteDTO;
import gruppo4.ALDAPAMA.entities.Utente;
import gruppo4.ALDAPAMA.enums.Ruolo;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Optional;

@Service
public class UtentiService {

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private UtentiRepository utentiRepo;

    public Page<Utente> getAll(int page, int size, String sortBy) {
        if (size > 25) size = 25;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepo.findAll(pageable);
    }

    public Utente getById(long userId) {
        return this.utentiRepo.findById(userId).orElseThrow(() -> new NotFoundException("Utente con id " + userId + " non trovato!"));
    }

    public Utente saveNewUtente(NewUtenteDTO newUtenteDTO) {
        this.utentiRepo.
                findByUsername(newUtenteDTO.username()).
                ifPresent(utente -> {
                    throw new BadRequestException("L'utente con username " + newUtenteDTO.username() + " esiste già");
                });

        this.utentiRepo.
                findByEmail(newUtenteDTO.email()).
                ifPresent(utente -> {
                    throw new BadRequestException("L'utente con email " + newUtenteDTO.email() + " esiste già");
                });


        Utente nuovoUtente = new Utente(newUtenteDTO);
//        nuovoUtente.setPassword(bcrypt.encode(nuovoUtente.getPassword()));

        return this.utentiRepo.save(nuovoUtente);
    }

    public Utente updateUser(long idToUpdate, NewUtenteDTO newUtenteDTO) {
        Utente found = this.getById(idToUpdate);

        if (!found.getUsername().equals(newUtenteDTO.username())) {
            Optional<Utente> searchUtenteByUsername = utentiRepo.findByUsername(newUtenteDTO.username());
            if (searchUtenteByUsername.isPresent() && searchUtenteByUsername.get().getId() != idToUpdate){
                throw new BadRequestException("L'username fornito è già in uso da un altro utente.");
            }
        }

        if (!found.getEmail().equals(newUtenteDTO.email())) {
            Optional<Utente> searchUtenteByEmail = utentiRepo.findByEmail(newUtenteDTO.email());
            if (searchUtenteByEmail.isPresent() && searchUtenteByEmail.get().getId() != idToUpdate){
                throw new BadRequestException("L'email fornito è già in uso da un altro utente.");
            }
        }

        found.setEmail(newUtenteDTO.email());
        found.setNome(newUtenteDTO.nome());
        found.setCognome(newUtenteDTO.cognome());
        found.setUsername(newUtenteDTO.username());
        found.setRuolo(Ruolo.stringToEnum(newUtenteDTO.ruolo()));

        return this.utentiRepo.save(found);
    }

    public void deleteUtente(long idToDelete) {
        this.utentiRepo.delete(
                this.getById(idToDelete)
        );
    }

    public Utente uploadAvatar(long employeeId, MultipartFile file) {

        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }

        Utente found = this.getById(employeeId);
        found.setAvatar(url);

        return this.utentiRepo.save(found);


    }
}
