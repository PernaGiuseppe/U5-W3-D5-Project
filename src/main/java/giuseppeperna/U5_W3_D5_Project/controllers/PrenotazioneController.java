package giuseppeperna.U5_W3_D5_Project.controllers;

import giuseppeperna.U5_W3_D5_Project.entities.Prenotazione;
import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.payloads.EventoResponseDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.PrenotazioneDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.PrenotazioneResponseDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.UtenteResponseDTO;
import giuseppeperna.U5_W3_D5_Project.services.PrenotazioneService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneResponseDTO createPrenotazione(@RequestBody @Validated PrenotazioneDTO body,
                                                      HttpServletRequest request) {
        Utente utente = (Utente) request.getAttribute("currentUser");
        Prenotazione prenotazione = prenotazioneService.createPrenotazione(body, utente);
        return convertToResponseDTO(prenotazione);
    }

    @GetMapping("/mie-prenotazioni")
    public List<PrenotazioneResponseDTO> getMiePrenotazioni(HttpServletRequest request) {
        Utente utente = (Utente) request.getAttribute("currentUser");
        return prenotazioneService.findByUtente(utente.getId()).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/evento/{eventoId}")
    public List<PrenotazioneResponseDTO> getPrenotazioniEvento(@PathVariable Long eventoId) {
        return prenotazioneService.findByEvento(eventoId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private PrenotazioneResponseDTO convertToResponseDTO(Prenotazione prenotazione) {
        UtenteResponseDTO utenteDTO = new UtenteResponseDTO(
                prenotazione.getUtente().getId(),
                prenotazione.getUtente().getNome(),
                prenotazione.getUtente().getCognome(),
                prenotazione.getUtente().getEmail(),
                prenotazione.getUtente().getRuolo()
        );

        UtenteResponseDTO organizzatoreDTO = new UtenteResponseDTO(
                prenotazione.getEvento().getOrganizzatore().getId(),
                prenotazione.getEvento().getOrganizzatore().getNome(),
                prenotazione.getEvento().getOrganizzatore().getCognome(),
                prenotazione.getEvento().getOrganizzatore().getEmail(),
                prenotazione.getEvento().getOrganizzatore().getRuolo()
        );

        EventoResponseDTO eventoDTO = new EventoResponseDTO(
                prenotazione.getEvento().getId(),
                prenotazione.getEvento().getTitolo(),
                prenotazione.getEvento().getDescrizione(),
                prenotazione.getEvento().getData(),
                prenotazione.getEvento().getOra(),
                prenotazione.getEvento().getLuogo(),
                prenotazione.getEvento().getPostiDisponibili(),
                prenotazione.getEvento().getPostiTotali(),
                organizzatoreDTO
        );

        return new PrenotazioneResponseDTO(
                prenotazione.getId(),
                utenteDTO,
                eventoDTO,
                prenotazione.getNumeroPosti(),
                prenotazione.getDataPrenotazione()
        );
    }
}
