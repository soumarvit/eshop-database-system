package cvut.fel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JeulozenId implements Serializable {
    private static final long serialVersionUID = 7526758863697198642L;
    @Column(name = "id_produkt", nullable = false)
    private Integer idProdukt;

    @Column(name = "id_sklad", nullable = false)
    private Integer idSklad;

    public Integer getIdProdukt() {
        return idProdukt;
    }

    public void setIdProdukt(Integer idProdukt) {
        this.idProdukt = idProdukt;
    }

    public Integer getIdSklad() {
        return idSklad;
    }

    public void setIdSklad(Integer idSklad) {
        this.idSklad = idSklad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JeulozenId entity = (JeulozenId) o;
        return Objects.equals(this.idProdukt, entity.idProdukt) &&
                Objects.equals(this.idSklad, entity.idSklad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProdukt, idSklad);
    }

}