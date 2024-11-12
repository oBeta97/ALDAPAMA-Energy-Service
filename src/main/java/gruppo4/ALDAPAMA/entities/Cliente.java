package gruppo4.ALDAPAMA.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "clienti")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // Constructor for creating new cliente
    public Cliente(String ragioneSociale, String partitaIva, String pec, 
                  String telefono, String logoAziendale) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.pec = pec;
        this.telefono = telefono;
        this.logoAziendale = logoAziendale;
        this.dataInserimento = new Date();
    }

    // Constructor for updating existing cliente
    public Cliente(Long id, String ragioneSociale, String pec, 
                  String telefono, Date dataUltimoContatto, 
                  String logoAziendale, Date dataInserimento) {
        this.id = id;
        this.ragioneSociale = ragioneSociale;
        this.pec = pec;
        this.telefono = telefono;
        this.dataUltimoContatto = dataUltimoContatto;
        this.logoAziendale = logoAziendale;
        this.dataInserimento = dataInserimento;
    }
}
