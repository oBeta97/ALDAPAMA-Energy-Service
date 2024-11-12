package gruppo4.ALDAPAMA.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ComuneDTO(@NotEmpty(message = "nome comune obbligatoria")
                        String nome,
                        @NotNull(message = "Devi mettere un id della provincia")
                        long id_provincia) {
}
