package gruppo4.ALDAPAMA.dto;

import gruppo4.ALDAPAMA.enums.TipoSede;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IndirizzoDTO(
        @NotEmpty(message = "La via è obbligatoria")
        String via,
        @NotNull(message = "Numero civico obbligatorio")
        long civico,
        @NotEmpty(message = "il CAP è obbligatorio")
        String cap,
        String tipoSede,
        @NotNull(message = "Numero civico obbligatorio")
        long id_comune,
        @NotNull(message = "Numero civico obbligatorio")
        long id_cliente
) {}
