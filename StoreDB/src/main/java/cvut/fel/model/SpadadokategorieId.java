package cvut.fel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SpadadokategorieId implements Serializable {
    private static final long serialVersionUID = 7237979820639651390L;
    @Column(name = "id_produkt", nullable = false)
    private Integer idProdukt;

    @Column(name = "id_kategorie", nullable = false)
    private Integer idKategorie;

    public Integer getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Integer idProdukt) {
        this.idProdukt = idProdukt;
    }

    public Integer getIdKategorie() {
        return idKategorie;
    }

    public void setIdKategorie(Integer idKategorie) {
        this.idKategorie = idKategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SpadadokategorieId entity = (SpadadokategorieId) o;
        return Objects.equals(this.idProdukt, entity.idProdukt) &&
                Objects.equals(this.idKategorie, entity.idKategorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProdukt, idKategorie);
    }

}