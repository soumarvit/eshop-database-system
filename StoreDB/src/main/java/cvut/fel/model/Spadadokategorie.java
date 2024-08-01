package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "spadadokategorie")
public class Spadadokategorie {
    @EmbeddedId
    private SpadadokategorieId id;

    @MapsId("idProdukt")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produkt", nullable = false)
    private Produkt idProdukt;

    @MapsId("idKategorie")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_kategorie", nullable = false)
    private Kategorie idKategorie;

    public SpadadokategorieId getId() {
        return id;
    }

    public void setId(SpadadokategorieId id) {
        this.id = id;
    }

    public Produkt getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Produkt idProdukt) {
        this.idProdukt = idProdukt;
    }

    public Kategorie getIdKategorie() {
        return idKategorie;
    }

    public void setIdKategorie(Kategorie idKategorie) {
        this.idKategorie = idKategorie;
    }

}