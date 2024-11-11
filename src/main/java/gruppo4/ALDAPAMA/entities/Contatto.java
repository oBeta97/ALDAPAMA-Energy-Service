package gruppo4.ALDAPAMA.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contatti")
@NoArgsConstructor
@Setter
@Getter
public class Contatto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nome;
    private String cognome;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}