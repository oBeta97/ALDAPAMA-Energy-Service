package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.ProvinciaServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/*
    http://localhost:3002/provincia
 */
@RestController
@RequestMapping("/provincia")
public class ProvinciaController {
    @Autowired
    private ProvinciaServ provinciaServ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Provincia saveProvincia(@RequestBody ProvinciaDTO body,
                                   BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestException("Ci sono errori nel payload! " + msg);
        }
        return this.provinciaServ.saveProvincia(body);
    }

    @GetMapping
    public Page<Provincia> findAllProvince(@RequestParam)
}
