package gruppo4.ALDAPAMA.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@NoArgsConstructor
public class Indizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String via;
    private long civico;
    private long CAP;
    @ManyToOne
    @JoinColumn(name = "id_comune",referencedColumnName = "id")
    private Comune comune;
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    public Indizzo(long CAP, long civico, Cliente cliente, Comune comune, String via) {
        this.CAP = CAP;
        this.civico = civico;
        this.cliente = cliente;
        this.comune = comune;
        this.via = via;
    }

}
