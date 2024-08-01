package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@Table(name = "sklad")
public class Sklad {
    @Id
    @ColumnDefault("nextval('sklad_id_sklad_seq'::regclass)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sklad", nullable = false)
    private Integer id;

    @Column(name = "lokace", nullable = false)
    private String lokace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLokace() {
        return lokace;
    }

    public void setLokace(String lokace) {
        this.lokace = lokace;
    }

}