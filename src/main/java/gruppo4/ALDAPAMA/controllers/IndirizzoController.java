package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.IndirizzoDTO;
import gruppo4.ALDAPAMA.entities.Indirizzo;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.IndirizzoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/*
 http://localhost:3002/indirizzi
 */
@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {
    @Autowired
    private IndirizzoServ indirizzoServ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Indirizzo saveIndirizzo(@RequestBody @Validated IndirizzoDTO body,
                                   BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestException("Ci sono errori nel payload! " + msg);
        }
        return this.indirizzoServ.saveIndirizzo(body);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Page<Indirizzo> findAllIndirizzi(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "30") int size) {
        return this.indirizzoServ.findAllIndirizzi(page, size);
    }

    @GetMapping("/{id_indirizzo}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Indirizzo findComuneById(@PathVariable long id_indirizzo) {
        return this.indirizzoServ.findIndirizzoById(id_indirizzo);
    }

    @PutMapping("/{id_indirizzo}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Indirizzo findIndirizzoByIdAndUp(@PathVariable long id_indirizzo,
                                            @RequestBody @Validated IndirizzoDTO body,
                                            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + msg);
        }
        return this.indirizzoServ.findIndirizzoByIdAndUp(id_indirizzo, body);
    }

    @DeleteMapping("/{id_indirizzo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void findIndizzoByIdAndDel(@PathVariable long id_indirizzo) {
        this.indirizzoServ.findIndizzoByIdAndDel(id_indirizzo);
    }
}
