package gruppo4.ALDAPAMA.entities;


import gruppo4.ALDAPAMA.dto.FatturaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate data;
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


    public Fattura (FatturaDTO dto, Utente utenteFattura, Cliente clienteFattura, StatoFattura statoFattura){
        this.data = dto.data();
        this.importo = dto.importo();
        this.numFatt = dto.numFatt();
        this.stato = statoFattura;
        this.utente = utenteFattura;
        this.cliente = clienteFattura;
    }

    public Fattura(LocalDate data, Long importo, String numFatt, StatoFattura stato, Utente utente, Cliente cliente) {
        this.data = data;
        this.importo = importo;
        this.numFatt = numFatt;
        this.stato = stato;
        this.utente = utente;
        this.cliente = cliente;
    }

    //num fattura deve avere num seriale/anno
}