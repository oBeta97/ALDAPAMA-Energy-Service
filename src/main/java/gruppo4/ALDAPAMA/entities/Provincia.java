package gruppo4.ALDAPAMA.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Provincia {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String sigla;

    public Provincia(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }
}
