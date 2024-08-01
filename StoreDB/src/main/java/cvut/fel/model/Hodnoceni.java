package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "hodnoceni")
public class Hodnoceni {
    @EmbeddedId
    private HodnoceniId id;

    @MapsId("idUzivatel")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_uzivatel", nullable = false)
    private Uzivatel idUzivatel;

    @MapsId("idProdukt")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produkt", nullable = false)
    private Produkt idProdukt;

    @Column(name = "popis", nullable = false, length = Integer.MAX_VALUE)
    private String popis;

    @Column(name = "hvezdy", nullable = false)
    private Integer hvezdy;

    public HodnoceniId getId() {
        return id;
    }

    public void setId(HodnoceniId id) {
        this.id = id;
    }

    public Uzivatel getIdUzivatel() {
        return idUzivatel;
    }

    public void setIdUzivatel(Uzivatel idUzivatel) {
        this.idUzivatel = idUzivatel;
    }

    public Produkt getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Produkt idProdukt) {
        this.idProdukt = idProdukt;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public Integer getHvezdy() {
        return hvezdy;
    }

    public void setHvezdy(Integer hvezdy) {
        this.hvezdy = hvezdy;
    }

}