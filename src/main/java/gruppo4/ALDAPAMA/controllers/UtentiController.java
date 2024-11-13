package gruppo4.ALDAPAMA.controllers;


import gruppo4.ALDAPAMA.dto.NewUtenteDTO;
import gruppo4.ALDAPAMA.entities.Utente;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.UtentiService;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
http://localhost:3002/utenti
 */

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;

    @GetMapping
    public Page<Utente> getAllUtenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return this.utentiService.getAll(page,size,sortBy);
    }

    @GetMapping("/{utenteId}")
    public Utente getUtente (@PathVariable long utenteId){
        return this.utentiService.getById(utenteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utente saveNewUtente (@RequestBody @Validated NewUtenteDTO newUtenteDTO, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload dell'utente!");
        }

        return this.utentiService.saveNewUtente(newUtenteDTO);
    }

    @PutMapping("/{utenteId}")
    public Utente updateUtente(@PathVariable long utenteId, @RequestBody @Validated NewUtenteDTO newUtenteDTO, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload dell'utente!");
        }

        return this.utentiService.updateUser(utenteId, newUtenteDTO);
    }


    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtente(@PathVariable long utenteId){
        this.utentiService.deleteUtente(utenteId);
    }

}
