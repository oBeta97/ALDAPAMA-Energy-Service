package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProvinciaDTO(
        @NotEmpty
        String nome,
        String sigla) {
}
