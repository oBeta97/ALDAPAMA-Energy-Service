package gruppo4.ALDAPAMA.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stati_fattura")
@NoArgsConstructor
@Setter
@Getter
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizione;

    public StatoFattura(String descrizione) {
        this.descrizione = descrizione;
    }
}