package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ContattoDTO(

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotEmpty(message = "Il nome è obbligatorio")
        @Length(max = 50, message = "Il nome non deve superare i 50 caratteri")
        String nome,

        @NotEmpty(message = "Il cognome è obbligatorio")
        @Length(max = 50, message = "Il cognome non deve superare i 50 caratteri")
        String cognome,

        @Length(max = 25, message = "Il numero di telefono non deve superare i 25 caratteri")
        String telefono,

        @NotNull(message = "L'ID cliente è obbligatorio")
        Long clienteId
) {
}
