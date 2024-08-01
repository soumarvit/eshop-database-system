package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "produkt")
public class Produkt {
    @Id
    @ColumnDefault("nextval('produkt_id_produkt_seq'::regclass)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produkt", nullable = false)
    private Integer id;

    @Column(name = "nazev", nullable = false)
    private String nazev;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "vyrobce", nullable = false)
    private String vyrobce;

    @Column(name = "popis", nullable = false, length = Integer.MAX_VALUE)
    private String popis;

    @Column(name = "cena", nullable = false, precision = 9, scale = 2)
    private BigDecimal cena;


    //////////////////////////////////////////////
    @ManyToMany(mappedBy = "favoriteProducts")
    Set<Uzivatel> likedBy;
    //////////////////////////////////////////////

    //////////////////////////////////////////////
    @ManyToMany(mappedBy = "managedProducts")
    Set<Zamestnanec> managedBy;
    //////////////////////////////////////////////

    //////////////////////////////////////////////
    @ManyToMany
    @JoinTable(
            name = "spadadokategorie",
            joinColumns = @JoinColumn(name = "id_produkt"),
            inverseJoinColumns = @JoinColumn(name = "id_kategorie"))
    Set<Kategorie> categories;
    //////////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getModel() {
        return model;
    }

    public Set<Uzivatel> getLikedBy() {
        return likedBy;
    }

    public Set<Zamestnanec> getManagedBy() {
        return managedBy;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVyrobce() {
        return vyrobce;
    }

    public void setVyrobce(String vyrobce) {
        this.vyrobce = vyrobce;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

}