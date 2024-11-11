package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

public record ClienteDTO(
        Long id,
        @NotEmpty(message = "La ragione sociale è obbligatoria")
        String ragioneSociale,
        @NotEmpty(message = "La partita IVA è obbligatoria")
        String partitaIva,
        @NotNull(message = "La data di inserimento è obbligatoria")
        Date dataInserimento,
        Date dataUltimoContatto,
        @Email
        String pec,
        String telefono,
        @URL
        String logoAziendale
) {
}
