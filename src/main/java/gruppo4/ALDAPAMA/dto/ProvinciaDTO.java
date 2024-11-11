package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProvinciaDTO(
        @NotEmpty(message = "nome provincia obbligatoria")
        String nome,
        @NotEmpty(message = "sigla provincia obbligatoria")
        String sigla) {}
