package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.ComuneDTO;
import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.ComuneServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/*
    http://localhost:3002/comune
 */
@RestController
@RequestMapping("/comune")
public class ComuneController {
    @Autowired
    private ComuneServ comuneServ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comune saveComune(@RequestBody @Validated ComuneDTO body,
                             BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestException("Ci sono errori nel payload! " + msg);
        }
        return this.comuneServ.saveComune(body);
    }

    @GetMapping
    public Page<Comune> findAllComuni(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "30") int size) {
        return this.comuneServ.findAllComuni(page, size);
    }

    @GetMapping("/{id_comune}")
    public Comune findComuneById(@PathVariable long id_comune) {
        return this.comuneServ.findComuneById(id_comune);
    }

    @PutMapping("/{id_comune}")
    public Comune findComuneByIdAndUp(@PathVariable long id_comune,
                                      @RequestBody @Validated ComuneDTO body,
                                      BindingResult validationResult){
        if (validationResult.hasErrors()){
            String msg = validationResult.getAllErrors().stream().map(objectError ->
                    objectError.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new BadRequestException("Ci sono stati errori nel payload! "+ msg);
        }
        return this.comuneServ.findComuneByIdAndUp(id_comune,body);
    }
}
