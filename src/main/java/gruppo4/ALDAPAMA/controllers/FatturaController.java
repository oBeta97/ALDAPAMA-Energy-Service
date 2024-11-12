package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.FatturaDTO;
import gruppo4.ALDAPAMA.entities.Fattura;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    @Autowired
    private FattureService fattureService;

    @GetMapping
    public Page<Fattura> getAllFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return this.fattureService.getAll(page,size,sortBy);
    }

    @GetMapping("/{fatturaId}")
    public Fattura getFattura (@PathVariable long fatturaId){
        return this.fattureService.getById(fatturaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fattura saveNewFattura (@RequestBody @Validated FatturaDTO newFatturaDTO, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload della fattura!");
        }

        return this.fattureService.saveNewFattura(newFatturaDTO);
    }

    @PutMapping("/{fatturaId}")
    public Fattura updateFattura(@PathVariable long fatturaId, @RequestBody @Validated FatturaDTO newFatturaDTO, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload della fattura!");
        }

        return this.fattureService.updateFattura(fatturaId, newFatturaDTO);
    }


    @DeleteMapping("/{fatturaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFattura(@PathVariable long fatturaId){
        this.fattureService.deleteFattura(fatturaId);
    }


}
