package gruppo4.ALDAPAMA.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comuni")
@Getter
@Setter
@NoArgsConstructor
public class Comune {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    public Comune(String nome, Provincia provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }
}
