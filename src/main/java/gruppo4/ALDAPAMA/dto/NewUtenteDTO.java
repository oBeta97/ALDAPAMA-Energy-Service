package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.NotEmpty;

public record NewUtenteDTO(
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
