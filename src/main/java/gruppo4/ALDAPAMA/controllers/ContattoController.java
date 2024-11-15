package gruppo4.ALDAPAMA.controllers;


import gruppo4.ALDAPAMA.dto.ContattoDTO;
import gruppo4.ALDAPAMA.entities.Contatto;
import gruppo4.ALDAPAMA.services.ContattoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contatti")
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class ContattoController {

    @Autowired
    private ContattoService contattoService;

    @GetMapping
    public Page<Contatto> getAllContatti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return contattoService.getAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Contatto getContattoById(@PathVariable Long id) {
        return contattoService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contatto createContatto(@Valid @RequestBody ContattoDTO body) {
        return contattoService.create(body);
    }

    @PutMapping("/{id}")
    public Contatto updateContatto(@PathVariable Long id, @Valid @RequestBody ContattoDTO body) {
        return contattoService.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContatto(@PathVariable Long id) {
        contattoService.delete(id);
    }
}
