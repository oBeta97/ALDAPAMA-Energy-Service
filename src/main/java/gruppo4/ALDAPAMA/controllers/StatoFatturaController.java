package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.FatturaDTO;
import gruppo4.ALDAPAMA.dto.StatoFatturaDTO;
import gruppo4.ALDAPAMA.entities.StatoFattura;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.FattureService;
import gruppo4.ALDAPAMA.services.StatiFatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statifattura")
public class StatoFatturaController {

    @Autowired
    private StatiFatturaService statiFatturaService;

    @GetMapping
    public Page<StatoFattura> getAllStatiFattura(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return this.statiFatturaService.getAll(page,size,sortBy);
    }

    @GetMapping("/{statoFatturaId}")
    public StatoFattura getFattura (@PathVariable long statoFatturaId){
        return this.statiFatturaService.getById(statoFatturaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatoFattura saveStatoFattura (@RequestBody @Validated StatoFatturaDTO newFatturaDTO, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload della StatoFattura!");
        }

        return this.statiFatturaService.saveNewStatoFattura(newFatturaDTO);
    }

    @PutMapping("/{statoFatturaId}")
    public StatoFattura updateStatoFattura(@PathVariable long statoFatturaId, @RequestBody @Validated StatoFatturaDTO newFatturaDTO, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload della StatoFattura!");
        }

        return this.statiFatturaService.updateUser(statoFatturaId, newFatturaDTO);
    }


    @DeleteMapping("/{statoFatturaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStatoFattura(@PathVariable long statoFatturaId){
        this.statiFatturaService.deleteFattura(statoFatturaId);
    }


}
