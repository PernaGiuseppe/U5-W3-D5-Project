package giuseppeperna.U5_W3_D5_Project.payloads;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResponseDTO(
        Long id,
        String titolo,
        String descrizione,
        LocalDate data,
        LocalTime ora,
        String luogo,
        Integer postiDisponibili,
        Integer postiTotali,
        UtenteResponseDTO organizzatore
) {
}
