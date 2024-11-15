package gruppo4.ALDAPAMA.dto;

import gruppo4.ALDAPAMA.entities.Cliente;
import gruppo4.ALDAPAMA.entities.StatoFattura;
import gruppo4.ALDAPAMA.entities.Utente;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.Date;

public record FatturaDTO(
        LocalDate data,
        Long importo,
        String numFatt,
        long idStato,
        long idUtente,
        long idCliente
) {}
