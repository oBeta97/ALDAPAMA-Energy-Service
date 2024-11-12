package gruppo4.ALDAPAMA.dto;

import gruppo4.ALDAPAMA.enums.Ruolo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;

public record UtenteDTO(
        @NotEmpty(message = "Username is mandatory")
        String username,
        @NotEmpty(message = "Email is mandatory")
        String email,
        @NotEmpty(message = "Password is mandatory")
        String password,
        String nome,
        String cognome
) {
}
