package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.ComuneDTO;
import gruppo4.ALDAPAMA.dto.ProvinciaDTO;
import gruppo4.ALDAPAMA.entities.Comune;
import gruppo4.ALDAPAMA.entities.Provincia;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.services.ComuneServ;
import gruppo4.ALDAPAMA.services.ProvinciaServ;
import gruppo4.ALDAPAMA.tools.CSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    http://localhost:3002/comune
 */
@RestController
@RequestMapping("/comune")
public class ComuneController {
    @Autowired
    private ComuneServ comuneServ;

    @Autowired
    private ProvinciaServ provinciaServ;

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

    @PostMapping("/csv")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Comune> importComuniFromCSV (@RequestParam("csv") MultipartFile csvFile){

        List<ComuneDTO> res = new ArrayList<>();

        List<String[]> csv = CSV.toStringList(csvFile);
        for (String[] row : csv){

            String provinciaString = row[3];

            Provincia provincia = new Provincia();
            try{
                provincia = this.provinciaServ.findProvinciaByNome(provinciaString);
            } catch (NotFoundException e){
                switch (provinciaString){
                    case "Verbano-Cusio-Ossola":
                        provincia = this.correggiProvincia("Verbania","Verbano-Cusio-Ossola", "VB");
                        break;
                    case "Valle d'Aosta/Vallée d'Aoste":
                        provincia = this.correggiProvincia("Aosta", "Valle d'Aosta/Vallée d'Aoste","AO");
                        break;
                    case "Monza e della Brianza":
                        provincia = this.correggiProvincia("Monza-Brianza", "Monza e della Brianza", "MB");
                        break;
                    case "Bolzano/Bozen":
                        provincia = this.correggiProvincia("Bolzano","Bolzano/Bozen","BZ");
                        break;
                    case "La Spezia":
                        provincia = this.correggiProvincia("La-Spezia","La Spezia","SP");
                        break;
                    case "Reggio nell'Emilia":
                        provincia = this.correggiProvincia("Reggio-Emilia", "Reggio nell'Emilia","RE");
                        break;
                    case "Forlì-Cesena":
                        provincia = this.correggiProvincia("Forli-Cesena","Forlì-Cesena", "FC");
                        break;
                    case "Pesaro e Urbino":
                        provincia = this.correggiProvincia("Pesaro-Urbino","Pesaro e Urbino", "PU");
                        break;
                    case "Ascoli Piceno":
                        provincia = this.correggiProvincia("Ascoli-Piceno","Ascoli Piceno","AP");
                        break;
                    case "Reggio Calabria":
                        provincia = this.correggiProvincia("Reggio-Calabria","Reggio Calabria","RC");
                        break;
                    case "Vibo Valentia":
                        provincia = this.correggiProvincia("Vibo-Valentia","Vibo Valentia","VV");
                        break;
                    case "Sud Sardegna":
                        this.provinciaServ.saveProvincia(new ProvinciaDTO("Sud Sardegna", "SU"));
                        break;
                    default:
                        throw new NotFoundException(provinciaString +" non trovata");
                }
            }

            res.add(new ComuneDTO(row[2], provincia.getId()));
        }

        return this.comuneServ.saveComuneList(res);

    }

    private Provincia correggiProvincia (String nomeSbagliato, String nomeCorretto, String siglaCorretta){
        Provincia p = this.provinciaServ.findProvinciaByNome(nomeSbagliato);
        ProvinciaDTO provinciaCorretta = new ProvinciaDTO(nomeCorretto, siglaCorretta);
        return this.provinciaServ.findProvinciaByIdAndUp(p.getId(), provinciaCorretta);
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

    @DeleteMapping("/{id_comune}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findComuneByIdAndDel(@PathVariable long id_comune){
        this.comuneServ.findComuneByIdAndDel(id_comune);
    }
}
