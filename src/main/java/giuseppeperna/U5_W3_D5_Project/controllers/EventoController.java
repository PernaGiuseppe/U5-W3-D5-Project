package giuseppeperna.U5_W3_D5_Project.controllers;

import giuseppeperna.U5_W3_D5_Project.entities.Evento;
import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.payloads.EventoDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.EventoResponseDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.EventoUpdateDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.UtenteResponseDTO;
import giuseppeperna.U5_W3_D5_Project.services.EventoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoResponseDTO createEvento(@RequestBody @Validated EventoDTO body,
                                          HttpServletRequest request) {
        Utente utente = (Utente) request.getAttribute("currentUser");
        Evento evento = eventoService.createEvento(body, utente);
        return convertToResponseDTO(evento);
    }

    @GetMapping
    public List<EventoResponseDTO> getAllEventi() {
        return eventoService.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/disponibili")
    public List<EventoResponseDTO> getEventiDisponibili() {
        return eventoService.findEventiDisponibili().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventoResponseDTO getEventoById(@PathVariable Long id) {
        return convertToResponseDTO(eventoService.findById(id));
    }

    @GetMapping("/miei-eventi")
    public List<EventoResponseDTO> getMieiEventi(HttpServletRequest request) {
        Utente utente = (Utente) request.getAttribute("currentUser");
        return eventoService.findByOrganizzatore(utente.getId()).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public EventoResponseDTO updateEvento(@PathVariable Long id,
                                          @RequestBody @Validated EventoUpdateDTO body,
                                          HttpServletRequest request) {
        Utente utente = (Utente) request.getAttribute("currentUser");
        Evento evento = eventoService.updateEvento(id, body, utente);
        return convertToResponseDTO(evento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable Long id, HttpServletRequest request) {
        Utente utente = (Utente) request.getAttribute("currentUser");
        eventoService.deleteEvento(id, utente);
    }

    private EventoResponseDTO convertToResponseDTO(Evento evento) {
        UtenteResponseDTO organizzatoreDTO = new UtenteResponseDTO(
                evento.getOrganizzatore().getId(),
                evento.getOrganizzatore().getNome(),
                evento.getOrganizzatore().getCognome(),
                evento.getOrganizzatore().getEmail(),
                evento.getOrganizzatore().getRuolo()
        );

        return new EventoResponseDTO(
                evento.getId(),
                evento.getTitolo(),
                evento.getDescrizione(),
                evento.getData(),
                evento.getOra(),
                evento.getLuogo(),
                evento.getPostiDisponibili(),
                evento.getPostiTotali(),
                organizzatoreDTO
        );
    }
}

