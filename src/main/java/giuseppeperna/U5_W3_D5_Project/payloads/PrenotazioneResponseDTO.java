package giuseppeperna.U5_W3_D5_Project.payloads;

import java.time.LocalDateTime;

public record PrenotazioneResponseDTO(
        Long id,
        UtenteResponseDTO utente,
        EventoResponseDTO evento,
        Integer numeroPosti,
        LocalDateTime dataPrenotazione
) {
}

