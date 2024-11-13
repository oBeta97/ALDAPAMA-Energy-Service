package gruppo4.ALDAPAMA.entities;

import gruppo4.ALDAPAMA.enums.TipoSede;
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
public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String via;
    private long civico;
    private String CAP;
    @Enumerated(EnumType.STRING)
    private TipoSede tipoSede;
    @ManyToOne
    @JoinColumn(name = "id_comune",referencedColumnName = "id")
    private Comune comune;
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;



    public Indirizzo(String CAP, long civico, Cliente cliente, Comune comune, TipoSede tipoSede, String via) {
        this.CAP = CAP;
        this.civico = civico;
        this.cliente = cliente;
        this.comune = comune;
        this.tipoSede = tipoSede;
        this.via = via;
    }
}
