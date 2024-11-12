package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.IndirizzoDTO;
import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.entities.Indizzo;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.IndirizzoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public Indizzo saveIndirizzo(@RequestBody @Validated IndirizzoDTO body,
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
    public Page<Indizzo> findAllIndirizzi(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "30") int size) {
        return this.indirizzoServ.findAllIndirizzi(page, size);
    }

    @GetMapping("/{id_indirizzo}")
    public Indizzo findComuneById(@PathVariable long id_indirizzo) {
        return this.indirizzoServ.findIndirizzoById(id_indirizzo);
    }
}