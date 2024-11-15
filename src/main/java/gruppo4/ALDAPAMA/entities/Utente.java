package gruppo4.ALDAPAMA.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gruppo4.ALDAPAMA.dto.NewUtenteDTO;
import gruppo4.ALDAPAMA.enums.Ruolo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utenti")
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "ruolo", "authorities"})
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String nome;
    private String cognome;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    public Utente(String username, String email, String password, String nome, String cognome, Ruolo ruolo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.setDefaultAvatar();
        this.ruolo = ruolo;
    }

    public Utente(NewUtenteDTO newUtenteDTO) {
        this.username = newUtenteDTO.username();
        this.email = newUtenteDTO.email();
        this.password = newUtenteDTO.password();
        this.nome = newUtenteDTO.nome();
        this.cognome = newUtenteDTO.cognome();
        this.setDefaultAvatar();
        this.ruolo = Ruolo.USER;
    }

    private void setDefaultAvatar() {
        this.avatar = "https://ui-avatars.com/api/?name=" + getNome() + "+" + getCognome();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }
}