package cvut.fel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HodnoceniId implements Serializable {
    private static final long serialVersionUID = 1365480290533449452L;
    @Column(name = "id_uzivatel", nullable = false)
    private Integer idUzivatel;

    @Column(name = "id_produkt", nullable = false)
    private Integer idProdukt;

    public Integer getIdUzivatel() {
        return idUzivatel;
    }

    public void setIdUzivatel(Integer idUzivatel) {
        this.idUzivatel = idUzivatel;
    }

    public Integer getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Integer idProdukt) {
        this.idProdukt = idProdukt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HodnoceniId entity = (HodnoceniId) o;
        return Objects.equals(this.idProdukt, entity.idProdukt) &&
                Objects.equals(this.idUzivatel, entity.idUzivatel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProdukt, idUzivatel);
    }

}