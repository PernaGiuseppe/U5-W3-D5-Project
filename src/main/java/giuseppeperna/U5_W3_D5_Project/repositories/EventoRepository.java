package giuseppeperna.U5_W3_D5_Project.repositories;

import giuseppeperna.U5_W3_D5_Project.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByOrganizzatoreId(Long organizzatoreId);

    List<Evento> findByDataAfter(LocalDate data);

    List<Evento> findByPostiDisponibiliGreaterThan(Integer posti);
}
