package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.ClienteDTO;
import gruppo4.ALDAPAMA.entities.Cliente;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Page<Cliente> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }

    public Cliente getById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con id: " + id + " non trovato"));
    }

    public Cliente create(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByPartitaIva(clienteDTO.partitaIva())) {
            throw new BadRequestException("Partita IVA già esistente");
        }
        if (clienteRepository.existsByPec(clienteDTO.pec())) {
            throw new BadRequestException("PEC già esistente");
        }

        Cliente cliente = new Cliente(
                clienteDTO.ragioneSociale(),
                clienteDTO.partitaIva(),
                clienteDTO.pec(),
                clienteDTO.telefono(),
                clienteDTO.logoAziendale()
        );

        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, ClienteDTO clienteDTO) {
        Cliente clienteToUp = getById(id);

        if (clienteRepository.existsByPartitaIva(clienteDTO.partitaIva())) {
            throw new BadRequestException("Partita IVA già esistente");
        }
        if (clienteRepository.existsByPec(clienteDTO.pec())) {
            throw new BadRequestException("PEC già esistente");
        }

        clienteToUp.setRagioneSociale(clienteDTO.ragioneSociale());
        clienteToUp.setPartitaIva(clienteDTO.partitaIva());
        clienteToUp.setPec(clienteDTO.pec());
        clienteToUp.setTelefono(clienteDTO.telefono());
        clienteToUp.setLogoAziendale(clienteDTO.logoAziendale());

        return this.clienteRepository.save(clienteToUp);
    }

    public void delete(Long id) {
        Cliente cliente = getById(id);
        clienteRepository.delete(cliente);
    }
}
