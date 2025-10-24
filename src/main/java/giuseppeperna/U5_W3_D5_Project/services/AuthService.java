package giuseppeperna.U5_W3_D5_Project.services;

import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.exceptions.BadRequestException;
import giuseppeperna.U5_W3_D5_Project.exceptions.UnauthorizedException;
import giuseppeperna.U5_W3_D5_Project.payloads.UtenteDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.UtenteLoginDTO;
import giuseppeperna.U5_W3_D5_Project.repositories.UtenteRepository;
import giuseppeperna.U5_W3_D5_Project.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private JWTTools jwtTools;

    public Utente register(UtenteDTO body) {
        if (utenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("Email già registrata");
        }

        Utente utente = new Utente();
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword(body.password());
        utente.setRuolo(body.ruolo());

        return utenteRepository.save(utente);
    }

    public String login(UtenteLoginDTO body) {
        Utente utente = utenteRepository.findByEmail(body.email())
                .orElseThrow(() -> new UnauthorizedException("Credenziali non valide"));

        if (!utente.getPassword().equals(body.password())) {
            throw new UnauthorizedException("Credenziali non valide");
        }

        return jwtTools.createToken(utente);
    }
}

