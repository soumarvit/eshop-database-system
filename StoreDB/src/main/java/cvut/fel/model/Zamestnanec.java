package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "zamestnanec")
public class Zamestnanec {
    @Id
    @Column(name = "id_uzivatel", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_uzivatel", nullable = false)
    private Uzivatel uzivatel;

    @Column(name = "rodnecislo", nullable = false, length = 11)
    private String rodnecislo;

    //////////////////////////////////////////////
    @ManyToMany
    @JoinTable(
            name = "spravuje",
            joinColumns = @JoinColumn(name = "id_zamestnanec"),
            inverseJoinColumns = @JoinColumn(name = "id_produkt"))
    Set<Produkt> managedProducts;
    //////////////////////////////////////////////

    public Zamestnanec(){
        managedProducts = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uzivatel getUzivatel() {
        return uzivatel;
    }

    public void setUzivatel(Uzivatel uzivatel) {
        this.uzivatel = uzivatel;
    }

    public String getRodnecislo() {
        return rodnecislo;
    }

    public void setRodnecislo(String rodnecislo) {
        this.rodnecislo = rodnecislo;
    }

    public Set<Produkt> getManagedProducts() {
        return managedProducts;
    }
}