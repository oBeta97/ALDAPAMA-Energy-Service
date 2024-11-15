package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.ContattoDTO;
import gruppo4.ALDAPAMA.entities.Cliente;
import gruppo4.ALDAPAMA.entities.Contatto;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.exceptions.NotFoundException;
import gruppo4.ALDAPAMA.repositories.ClienteRepository;
import gruppo4.ALDAPAMA.repositories.ContattoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContattoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContattoRepository contattoRepository;

    public Page<Contatto> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return contattoRepository.findAll(pageable);
    }

    public Contatto getById(Long id) {
        return contattoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contatto con id: " + id + " non trovato"));
    }

    public Contatto create(ContattoDTO body) {

        Cliente cliente = clienteRepository.findById(body.clienteId())
                .orElseThrow(() -> new NotFoundException(body.clienteId() + ""));
        this.contattoRepository.findByEmail(body.email()).ifPresent(

                contatto -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );

        Contatto newContatto = new Contatto(body.email(), body.nome(), body.cognome(), body.telefono(), cliente);


        return this.contattoRepository.save(newContatto);
    }

    public Contatto update(long contattoId, ContattoDTO body) {

        Contatto found = this.getById(contattoId);


        if (!found.getEmail().equals(body.email())) {
            this.contattoRepository.findByEmail(body.email()).ifPresent(

                    contatto -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }


        found.setEmail(body.email());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setTelefono(body.telefono());


        return this.contattoRepository.save(found);
    }


    public void delete(long contattoId) {
        Contatto found = this.getById(contattoId);
        this.contattoRepository.delete(found);
    }
}

