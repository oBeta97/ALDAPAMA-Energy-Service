package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
    @NotEmpty(message = "Username is required")
    String username,
    @NotEmpty(message = "Password is required")
    String password
) {}
