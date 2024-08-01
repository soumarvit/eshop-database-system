package cvut.fel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "objednavka")
public class Objednavka {
    @Id
    @ColumnDefault("nextval('objednavka_id_objednavka_seq'::regclass)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objednavka", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_uzivatel", nullable = false)
    private Uzivatel idUzivatel;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "cas", nullable = false)
    private LocalTime cas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uzivatel getIdUzivatel() {
        return idUzivatel;
    }

    public void setIdUzivatel(Uzivatel idUzivatel) {
        this.idUzivatel = idUzivatel;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getCas() {
        return cas;
    }

    public void setCas(LocalTime cas) {
        this.cas = cas;
    }

}