package giuseppeperna.U5_W3_D5_Project.controllers;

import giuseppeperna.U5_W3_D5_Project.entities.Utente;
import giuseppeperna.U5_W3_D5_Project.payloads.TokenDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.UtenteDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.UtenteLoginDTO;
import giuseppeperna.U5_W3_D5_Project.payloads.UtenteResponseDTO;
import giuseppeperna.U5_W3_D5_Project.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteResponseDTO register(@RequestBody @Validated UtenteDTO body) {
        Utente utente = authService.register(body);
        return new UtenteResponseDTO(
                utente.getId(),
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getRuolo()
        );
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Validated UtenteLoginDTO body) {
        String token = authService.login(body);
        return new TokenDTO(token);
    }
}
