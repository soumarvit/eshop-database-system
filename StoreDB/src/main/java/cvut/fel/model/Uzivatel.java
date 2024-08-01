package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "uzivatel")
public class Uzivatel {
    @Id
    @ColumnDefault("nextval('uzivatel_id_uzivatel_seq'::regclass)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzivatel", nullable = false)
    private Integer id;

    @Column(name = "jmeno", nullable = false, length = 64)
    private String jmeno;

    @Column(name = "heslo", nullable = false)
    private byte[] heslo;

    //////////////////////////////////////////////
    @ManyToMany
    @JoinTable(
            name = "oblibeneprodukty",
            joinColumns = @JoinColumn(name = "id_uzivatel"),
            inverseJoinColumns = @JoinColumn(name = "id_produkt"))
    Set<Produkt> favoriteProducts;
    //////////////////////////////////////////////

    public Uzivatel(){
        this.favoriteProducts = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public byte[] getHeslo() {
        return heslo;
    }

    public void setHeslo(byte[] heslo) {
        this.heslo = heslo;
    }

    public Set<Produkt> getFavoriteProducts() {
        return favoriteProducts;
    }
}