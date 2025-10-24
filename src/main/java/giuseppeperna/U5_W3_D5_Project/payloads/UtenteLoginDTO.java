package giuseppeperna.U5_W3_D5_Project.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UtenteLoginDTO(
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        String password
) {
}
