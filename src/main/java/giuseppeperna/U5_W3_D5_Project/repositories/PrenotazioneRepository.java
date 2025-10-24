package giuseppeperna.U5_W3_D5_Project.repositories;

import giuseppeperna.U5_W3_D5_Project.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtenteId(Long utenteId);

    List<Prenotazione> findByEventoId(Long eventoId);

    boolean existsByUtenteIdAndEventoId(Long utenteId, Long eventoId);
}
