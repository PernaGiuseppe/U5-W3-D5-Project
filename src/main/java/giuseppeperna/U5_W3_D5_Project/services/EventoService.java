package giuseppeperna.U5_W3_D5_Project.services;

import giuseppeperna.U5_W3_D5_Project.entities.Evento;
import giuseppeperna.U5_W3_D5_Project.entities.RuoloUtente;
import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.exceptions.NotFoundException;
import giuseppeperna.U5_W3_D5_Project.exceptions.UnauthorizedException;
import giuseppeperna.U5_W3_D5_Project.payloads.EventoDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.EventoUpdateDTO;
import giuseppeperna.U5_W3_D5_Project.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento createEvento(EventoDTO body, Utente organizzatore) {
        if (organizzatore.getRuolo() != RuoloUtente.ORGANIZZATORE_EVENTI) {
            throw new UnauthorizedException("Solo gli organizzatori possono creare eventi");
        }

        Evento evento = new Evento();
        evento.setTitolo(body.titolo());
        evento.setDescrizione(body.descrizione());
        evento.setData(body.data());
        evento.setOra(body.ora());
        evento.setLuogo(body.luogo());
        evento.setPostiTotali(body.postiTotali());
        evento.setPostiDisponibili(body.postiTotali());
        evento.setOrganizzatore(organizzatore);

        return eventoRepository.save(evento);
    }

    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    public List<Evento> findEventiDisponibili() {
        return eventoRepository.findByPostiDisponibiliGreaterThan(0);
    }

    public Evento findById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));
    }

    public List<Evento> findByOrganizzatore(Long organizzatoreId) {
        return eventoRepository.findByOrganizzatoreId(organizzatoreId);
    }

    public Evento updateEvento(Long id, EventoUpdateDTO body, Utente utente) {
        Evento evento = findById(id);

        if (!evento.getOrganizzatore().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non puoi modificare eventi di altri organizzatori");
        }

        if (body.titolo() != null) evento.setTitolo(body.titolo());
        if (body.descrizione() != null) evento.setDescrizione(body.descrizione());
        if (body.data() != null) evento.setData(body.data());
        if (body.ora() != null) evento.setOra(body.ora());
        if (body.luogo() != null) evento.setLuogo(body.luogo());

        if (body.postiTotali() != null) {
            int differenza = body.postiTotali() - evento.getPostiTotali();
            evento.setPostiTotali(body.postiTotali());
            evento.setPostiDisponibili(evento.getPostiDisponibili() + differenza);
        }

        return eventoRepository.save(evento);
    }

    public void deleteEvento(Long id, Utente utente) {
        Evento evento = findById(id);

        if (!evento.getOrganizzatore().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non puoi eliminare eventi di altri organizzatori");
        }

        eventoRepository.delete(evento);
    }
}
