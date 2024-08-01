package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "osobniudaje")
public class Osobniudaje {
    @Id
    @Column(name = "id_uzivatel", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_uzivatel", nullable = false)
    private Uzivatel uzivatel;

    @Column(name = "jmeno", nullable = false, length = 127)
    private String jmeno;

    @Column(name = "prijmeni", nullable = false, length = 127)
    private String prijmeni;

    @Column(name = "narozeni", nullable = false)
    private LocalDate narozeni;

    @Column(name = "obec", nullable = false, length = 127)
    private String obec;

    @Column(name = "ulice", nullable = false, length = 127)
    private String ulice;

    @Column(name = "cislopopisne", nullable = false, length = 15)
    private String cislopopisne;

    @Column(name = "telefon", length = 15)
    private String telefon;

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

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public LocalDate getNarozeni() {
        return narozeni;
    }

    public void setNarozeni(LocalDate narozeni) {
        this.narozeni = narozeni;
    }

    public String getObec() {
        return obec;
    }

    public void setObec(String obec) {
        this.obec = obec;
    }

    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public String getCislopopisne() {
        return cislopopisne;
    }

    public void setCislopopisne(String cislopopisne) {
        this.cislopopisne = cislopopisne;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

}