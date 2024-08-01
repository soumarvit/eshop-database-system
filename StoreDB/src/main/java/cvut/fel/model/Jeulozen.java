package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Table(name = "jeulozen")
public class Jeulozen {
    @EmbeddedId
    private JeulozenId id;

    @MapsId("idProdukt")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produkt", nullable = false)
    private Produkt idProdukt;

    @MapsId("idSklad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_sklad", nullable = false)
    private Sklad idSklad;

    @Column(name = "ks", nullable = false)
    private Integer ks;

    public JeulozenId getId() {
        return id;
    }

    public void setId(JeulozenId id) {
        this.id = id;
    }

    public Produkt getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Produkt idProdukt) {
        this.idProdukt = idProdukt;
    }

    public Sklad getIdSklad() {
        return idSklad;
    }

    public void setIdSklad(Sklad idSklad) {
        this.idSklad = idSklad;
    }

    public Integer getKs() {
        return ks;
    }

    public void setKs(Integer ks) {
        this.ks = ks;
    }

}