package giuseppeperna.U5_W3_D5_Project.services;

import giuseppeperna.U5_W3_D5_Project.entities.Evento;
import giuseppeperna.U5_W3_D5_Project.entities.Prenotazione;
import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.exceptions.BadRequestException;
import giuseppeperna.U5_W3_D5_Project.exceptions.NotFoundException;
import giuseppeperna.U5_W3_D5_Project.payloads.PrenotazioneDTO;
import giuseppeperna.U5_W3_D5_Project.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoService eventoService;

    @Transactional
    public Prenotazione createPrenotazione(PrenotazioneDTO body, Utente utente) {
        Evento evento = eventoService.findById(body.eventoId());

        if (prenotazioneRepository.existsByUtenteIdAndEventoId(utente.getId(), evento.getId())) {
            throw new BadRequestException("Hai già prenotato questo evento");
        }

        if (evento.getPostiDisponibili() < body.numeroPosti()) {
            throw new BadRequestException("Posti insufficienti disponibili");
        }

        evento.setPostiDisponibili(evento.getPostiDisponibili() - body.numeroPosti());

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);
        prenotazione.setNumeroPosti(body.numeroPosti());
        prenotazione.setDataPrenotazione(LocalDateTime.now());

        return prenotazioneRepository.save(prenotazione);
    }

    public List<Prenotazione> findByUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    public List<Prenotazione> findByEvento(Long eventoId) {
        return prenotazioneRepository.findByEventoId(eventoId);
    }

    public Prenotazione findById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));
    }
}
