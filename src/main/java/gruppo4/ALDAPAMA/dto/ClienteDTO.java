package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public record ClienteDTO(
        @NotEmpty(message = "La ragione sociale è obbligatoria")
        String ragioneSociale,
        @NotEmpty(message = "La partita IVA è obbligatoria")
        String partitaIva,
        Date dataUltimoContatto,
        @Email(message = "La mail non e nel formato corretto")
        String pec,
        String telefono,
        String logoAziendale) {
}
