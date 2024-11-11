package gruppo4.ALDAPAMA.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "clienti")
@NoArgsConstructor
@Setter
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ragione_sociale")
    private String ragioneSociale;
    @Column(name = "partita_iva", nullable = false)
    private String partitaIva;
    @Column(name = "data_inserimento", nullable = false)
    private Date dataInserimento;
    @Column(name = "data_ultimo_contatto")
    private Date dataUltimoContatto;
    private String pec;
    private String telefono;
    @Column(name = "logo_aziendale")
    private String logoAziendale;

}
