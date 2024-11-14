package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.ClienteDTO;
import gruppo4.ALDAPAMA.entities.Cliente;
import gruppo4.ALDAPAMA.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cliente", description = "Cliente management APIs")
@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
            summary = "Retrieve all clients",
            description = "Get a paginated list of all clients with sorting capabilities",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of clients")
            })
    @GetMapping
    public Page<Cliente> getAllClienti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return clienteService.getAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return clienteService.create(clienteDTO);
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        return clienteService.update(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
    }


    @GetMapping("/ordinatiby")
    @ResponseBody
    public List<Cliente> getOrdine(@RequestParam(defaultValue = "test") String ordine, @RequestParam(required = false) Integer anno) {

        switch (ordine.trim()) {
            case "nomeContatto":
                return clienteService.getClientiOrdinatiPerNomeContatto();
            case "fatturatoAnnuale":
                if (anno != null) {
                    return clienteService.getClientiOrdinatiPerFatturatoAnnuale(anno);
                } else {
                    throw new IllegalArgumentException("Anno non specificato per ordinamento per fatturato");
                }
            case "dataInserimento":
                return clienteService.getClientiOrdinatiPerDataInserimento();
            case "dataUltimoContatto":
                return clienteService.getClientiOrdinatiPerDataUltimoContatto();
            default:
                throw new IllegalArgumentException("Tipo di ordinamento non valido");
        }


    }
}
