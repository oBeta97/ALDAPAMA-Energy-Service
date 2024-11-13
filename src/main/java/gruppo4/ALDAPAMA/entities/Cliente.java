package gruppo4.ALDAPAMA.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "clienti")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
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

    public Cliente(String ragioneSociale, String partitaIva, String pec,
                   String telefono) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.pec = pec;
        this.telefono = telefono;
        this.logoAziendale = generateLogoAziendale(ragioneSociale);
        this.dataInserimento = new Date();
    }

    public Cliente(Long id, String ragioneSociale, String pec,
                   String telefono, Date dataUltimoContatto) {
        this.id = id;
        this.ragioneSociale = ragioneSociale;
        this.pec = pec;
        this.telefono = telefono;
        this.dataUltimoContatto = dataUltimoContatto;
        this.logoAziendale = generateLogoAziendale(ragioneSociale);
    }

    private String generateLogoAziendale(String ragioneSociale) {
        return "https://ui-avatars.com/api/?length=1&name=" + ragioneSociale;
    }

}
