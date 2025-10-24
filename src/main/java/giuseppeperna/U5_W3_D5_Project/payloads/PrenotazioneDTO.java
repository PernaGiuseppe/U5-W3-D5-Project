package giuseppeperna.U5_W3_D5_Project.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PrenotazioneDTO(
        @NotNull(message = "L'ID evento è obbligatorio")
        Long eventoId,

        @NotNull(message = "Il numero di posti è obbligatorio")
        @Positive(message = "Il numero di posti deve essere positivo")
        Integer numeroPosti
) {
}