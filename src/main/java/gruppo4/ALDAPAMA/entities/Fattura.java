package gruppo4.ALDAPAMA.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "fatture")
@NoArgsConstructor
@Getter
@Setter
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date data;
    @Column(nullable = false)
    private Long importo;
    @Column(name = "num_fatt", nullable = false)
    private String numFatt;

    @ManyToOne
    @JoinColumn(name = "id_stato")
    private StatoFattura stato;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}