package gruppo4.ALDAPAMA.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;

//    @Enumerated(EnumType.STRING)
//    private Ruolo ruolo;

}