package giuseppeperna.U5_W3_D5_Project.payloads;

import giuseppeperna.U5_W3_D5_Project.entities.RuoloUtente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UtenteDTO(
        @NotEmpty(message = "Il nome è obbligatorio")
        String nome,

        @NotEmpty(message = "Il cognome è obbligatorio")
        String cognome,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        String password,

        @NotNull(message = "Il ruolo è obbligatorio")
        RuoloUtente ruolo
) {
}
