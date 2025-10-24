package giuseppeperna.U5_W3_D5_Project.services;

import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.exceptions.NotFoundException;
import giuseppeperna.U5_W3_D5_Project.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente findById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }
}
