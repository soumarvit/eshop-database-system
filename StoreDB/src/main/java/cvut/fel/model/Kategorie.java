package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Table(name = "kategorie")
public class Kategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kategorie_id_gen")
    @SequenceGenerator(name = "kategorie_id_gen", sequenceName = "kategorie_id_kategorie_seq", allocationSize = 1)
    @Column(name = "id_kategorie", nullable = false)
    private Integer id;

    @Column(name = "nazev", nullable = false)
    private String nazev;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_nadkategorie")
    private Kategorie idNadkategorie;

    //////////////////////////////////////////////
    @ManyToMany(mappedBy = "categories")
    Set<Produkt> products;
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

    public Kategorie getIdNadkategorie() {
        return idNadkategorie;
    }

    public void setIdNadkategorie(Kategorie idNadkategorie) {
        this.idNadkategorie = idNadkategorie;
    }

    public Set<Produkt> getProducts() {
        return products;
    }
}