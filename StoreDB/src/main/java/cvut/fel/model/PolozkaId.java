package cvut.fel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class PolozkaId implements Serializable {
    private static final long serialVersionUID = 2026850872364955882L;
    @Column(name = "id_produkt", nullable = false)
    private Integer idProdukt;

    @Column(name = "id_objednavka", nullable = false)
    private Integer idObjednavka;

    @Column(name = "ks", nullable = false)
    private Integer ks;

    @Column(name = "cena", nullable = false, precision = 9, scale = 2)
    private BigDecimal cena;

    public Integer getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Integer idProdukt) {
        this.idProdukt = idProdukt;
    }

    public Integer getIdObjednavka() {
        return idObjednavka;
    }

    public void setIdObjednavka(Integer idObjednavka) {
        this.idObjednavka = idObjednavka;
    }

    public Integer getKs() {
        return ks;
    }

    public void setKs(Integer ks) {
        this.ks = ks;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PolozkaId entity = (PolozkaId) o;
        return Objects.equals(this.idProdukt, entity.idProdukt) &&
                Objects.equals(this.ks, entity.ks) &&
                Objects.equals(this.cena, entity.cena) &&
                Objects.equals(this.idObjednavka, entity.idObjednavka);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProdukt, ks, cena, idObjednavka);
    }

}