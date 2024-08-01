package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "polozka")
public class Polozka {
    @EmbeddedId
    private PolozkaId id;

    @MapsId("idProdukt")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produkt", nullable = false)
    private Produkt idProdukt;

    @MapsId("idObjednavka")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_objednavka", nullable = false)
    private Objednavka idObjednavka;

    public PolozkaId getId() {
        return id;
    }

    public void setId(PolozkaId id) {
        this.id = id;
    }

    public Produkt getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Produkt idProdukt) {
        this.idProdukt = idProdukt;
    }

    public Objednavka getIdObjednavka() {
        return idObjednavka;
    }

    public void setIdObjednavka(Objednavka idObjednavka) {
        this.idObjednavka = idObjednavka;
    }

}