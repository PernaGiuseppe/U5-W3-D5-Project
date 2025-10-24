package giuseppeperna.U5_W3_D5_Project.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoDTO(
        @NotEmpty(message = "Il titolo è obbligatorio")
        String titolo,

        @NotEmpty(message = "La descrizione è obbligatoria")
        String descrizione,

        @NotNull(message = "La data è obbligatoria")
        @Future(message = "La data deve essere futura")
        LocalDate data,

        @NotNull(message = "L'ora è obbligatoria")
        LocalTime ora,

        @NotEmpty(message = "Il luogo è obbligatorio")
        String luogo,

        @NotNull(message = "Il numero di posti è obbligatorio")
        @Positive(message = "Il numero di posti deve essere positivo")
        Integer postiTotali
) {
}
