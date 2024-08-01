package cvut.fel.store;

import cvut.fel.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    Connection connection;

    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction et;

    public Dao(){

    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://slon.felk.cvut.cz:5432/soumavit",
                "soumavit",
                "notmypassword"
        );

        emf = Persistence.createEntityManagerFactory("StoreDB");
        em = emf.createEntityManager();
        et = em.getTransaction();
    }

    public void disconnect() throws SQLException {
        connection.close();
        em.close();
        emf.close();
    }

    public Produkt getProdukt(int id){
        return em.find(Produkt.class, id);
    }

    public Produkt getProdukt(String nazev){
        return em.find(Produkt.class, nazev);
    }

    public Produkt createProdukt(String nazev, String model, String vyrobce, String popis, BigDecimal cena){
        et.begin();
        Produkt produkt = new Produkt();
        produkt.setNazev(nazev);
        produkt.setModel(model);
        produkt.setVyrobce(vyrobce);
        produkt.setPopis(popis);
        produkt.setCena(cena);
        em.persist(produkt);
        et.commit();
        return produkt;
    }

    public Uzivatel createUzivatel(String username, String password){
        et.begin();
        Uzivatel u = new Uzivatel();
        u.setJmeno(username);
        u.setHeslo(password.getBytes(StandardCharsets.UTF_8));
        em.persist(u);
        et.commit();
        return u;
    }

    public Objednavka createObjednavka(Uzivatel uzivatel, LocalDate date, LocalTime time){
        et.begin();
        Objednavka objednavka = new Objednavka();
        objednavka.setIdUzivatel(uzivatel);
        objednavka.setDatum(date); // Set the current date
        objednavka.setCas(time); // Set the current time
        em.persist(objednavka);
        et.commit();
        return objednavka;
    }

    public Objednavka createObjednavkaNoTransaction(Uzivatel uzivatel, LocalDate date, LocalTime time){
        Objednavka objednavka = new Objednavka();
        objednavka.setIdUzivatel(uzivatel);
        objednavka.setDatum(date); // Set the current date
        objednavka.setCas(time); // Set the current time
        em.persist(objednavka);
        return objednavka;
    }

    public void persist(Object o){
        em.persist(o);
    }

    public Produkt updateProduktPopis(int id, String updatedPopis){
        et.begin();
        Produkt produkt = em.find(Produkt.class, id);
        produkt.setPopis(updatedPopis);
        em.merge(produkt);
        et.commit();
        return produkt;
    }

    public Produkt updateProduktCena(int id, BigDecimal updatedCena){
        et.begin();
        Produkt produkt = em.find(Produkt.class, id);
        produkt.setCena(updatedCena);
        em.merge(produkt);
        et.commit();
        return produkt;
    }

    public Jeulozen removeKsFromSklad(int produktId, int skladId, int ks){
        ArrayList<Jeulozen> jeulozen = (ArrayList<Jeulozen>) this.getJeulozen(produktId);
        for (Jeulozen j : jeulozen){
            if (j.getKs() >= ks && j.getIdSklad().getId() == skladId){
                //remove ks of produkt from stock
                j.setKs(j.getKs() - ks);
                em.merge(j);
                return j;
            }
        }

        return null;
    }

    public void updateStockOfProduct(int produktId, int skladId, int ks){
        et.begin();
        ArrayList<Jeulozen> jeulozen = (ArrayList<Jeulozen>) this.getJeulozen(produktId);
        for (Jeulozen j : jeulozen){
            if (j.getIdSklad().getId() == skladId){
                j.setKs(ks);
                em.merge(j);
            }
        }
        et.commit();
    }

    public void beginTransaction(){
        et.begin();
    }

    public void commitTransaction(){
        et.commit();
    }

    public void rollbackTransaction(){
        et.rollback();
    }

    public List<Sklad> getAllSklad(){
        return em.createQuery("SELECT s FROM Sklad AS s", Sklad.class)
                .getResultList();
    }

    public void addProductToFavourites(Uzivatel u, Produkt p){
        et.begin();
        u.getFavoriteProducts().add(p);
        em.merge(u);
        et.commit();
    }


    public List<Zamestnanec> getEmployees(){
        return em.createQuery("SELECT z FROM Zamestnanec AS z", Zamestnanec.class)
                .getResultList();
    }

    public Zamestnanec getZamestnanec(int id){
        return em.createQuery("SELECT z FROM Zamestnanec AS z WHERE id=:uzivatelId", Zamestnanec.class)
                .setParameter("uzivatelId", id)
                .getSingleResult();
    }

    public Polozka createPolozkaNoTransaction(int produktId, int objednavkaId, int ks, BigDecimal cena){
        PolozkaId polozkaId = new PolozkaId();
        polozkaId.setIdProdukt(produktId);
        polozkaId.setIdObjednavka(objednavkaId);
        polozkaId.setKs(ks);
        polozkaId.setCena(cena);

        Polozka polozka = new Polozka();
        polozka.setId(polozkaId);
        polozka.setIdObjednavka(getObjednavka(objednavkaId));
        polozka.setIdProdukt(getProdukt(produktId));

        em.persist(polozka);
        return polozka;
    }


    public List<Polozka> getPolozkaFromOrder(int orerId){
        List<Polozka> polozky = em.createQuery("SELECT p FROM Polozka AS p WHERE idObjednavka.id=:orderId", Polozka.class)
                .setParameter("orderId", orerId)
                .getResultList();
        return polozky;
    }

    public List<Produkt> searchForProdukt(String search){
        return em.createQuery("SELECT p FROM Produkt AS p WHERE p.nazev LIKE :search", Produkt.class)
                .setParameter("search", search)
                .getResultList();
    }

    public Uzivatel getUzivatel(int id){
        return em.find(Uzivatel.class, id);
    }

    public Uzivatel getUzivatel(String jmeno){
        return em.createQuery("SELECT u FROM Uzivatel AS u WHERE u.jmeno=:jmeno", Uzivatel.class)
                .setParameter("jmeno", jmeno)
                .getSingleResult();
    }

    public void deleteUzivatel(String jmeno){
        et.begin();
        Uzivatel u = em.createQuery("SELECT u FROM Uzivatel AS u WHERE u.jmeno=:jmeno", Uzivatel.class)
                .setParameter("jmeno", jmeno)
                .getSingleResult();
        em.remove(u);
        et.commit();

    }

    public Objednavka getObjednavka(int id){
        return em.find(Objednavka.class, id);
    }

    public List<Jeulozen> getJeulozen(int produktId){
        return em.createQuery("SELECT ju FROM Jeulozen AS ju WHERE idProdukt.id=:produktId", Jeulozen.class)
                .setParameter("produktId", produktId)
                .getResultList();
    }

    public String getDate() throws SQLException {
        String currentDate = null;
        try (Statement s = connection.createStatement();
             ResultSet r = s.executeQuery("SELECT CURRENT_DATE")) {

            while (r.next()) {
                currentDate = r.getString(1);
            }
        }
        return currentDate;
    }

    public String getTime() throws SQLException {
        String currentTime = null;
        try (Statement s = connection.createStatement();
             ResultSet r = s.executeQuery("SELECT CURRENT_TIME")) {

            while (r.next()) {
                currentTime = r.getString(1);
            }
        }
        return currentTime;
    }


}
