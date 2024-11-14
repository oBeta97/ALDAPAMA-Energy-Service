package gruppo4.ALDAPAMA.services;

import gruppo4.ALDAPAMA.dto.LoginDTO;
import gruppo4.ALDAPAMA.dto.NewUtenteDTO;
import gruppo4.ALDAPAMA.dto.TokenDTO;
import gruppo4.ALDAPAMA.entities.Utente;
import gruppo4.ALDAPAMA.exceptions.UnauthorizedException;
import gruppo4.ALDAPAMA.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenDTO authenticateUser(LoginDTO loginDTO) {
        Utente utente = utentiService.findByUsername(loginDTO.username());
        if (passwordEncoder.matches(loginDTO.password(), utente.getPassword())) {
            String token = jwtTools.createToken(utente);
            return new TokenDTO(token);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    public Utente registerUser(NewUtenteDTO newUtenteDTO) {
        newUtenteDTO = new NewUtenteDTO(
                newUtenteDTO.username(),
                newUtenteDTO.email(),
                passwordEncoder.encode(newUtenteDTO.password()),
                newUtenteDTO.nome(),
                newUtenteDTO.cognome(),
                newUtenteDTO.ruolo()
        );
        Utente utente = utentiService.saveNewUtente(newUtenteDTO);
        return utente;
    }
}
