package giuseppeperna.U5_W3_D5_Project.payloads;

import giuseppeperna.U5_W3_D5_Project.entities.RuoloUtente;

public record UtenteResponseDTO(
        Long id,
        String nome,
        String cognome,
        String email,
        RuoloUtente ruolo
) {
}
