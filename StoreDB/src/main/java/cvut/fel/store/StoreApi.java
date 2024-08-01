package cvut.fel.store;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import cvut.fel.model.*;
import javafx.util.Pair;

public class StoreApi {

    private Dao dao;
    private ShoppingCart cart;
    private Uzivatel user;

    public StoreApi(){
        StoreLogger.initLogger();
        dao = new Dao();
        cart = new ShoppingCart();
        user = null;
    }

    public void startConnection() throws SQLException, ClassNotFoundException {
        StoreLogger.logStatus("Connecting to database...\n");
        dao.connect();
    }

    public void endConnection() throws SQLException, ClassNotFoundException {
        StoreLogger.logStatus("Disconnecting from database...\n");
        dao.disconnect();
    }


    public boolean login(String username, String password){

        StoreLogger.logStatus("Logging in...");
        if (user != null){
            StoreLogger.logUpdate("Already logged in\n");
            return false;
        }

        try{
            Uzivatel u = dao.getUzivatel(username);
            this.user = u;
            StoreLogger.logUpdate("Login successful\n");

            return true;

        } catch (Exception e){
            StoreLogger.logError("Invalid username or password\n");
            return false;
        }
    }


    public void logout(){
        StoreLogger.logStatus("Logging out...");
        if (user != null){
            user = null;
            StoreLogger.logUpdate("Logout successful\n");
            this.cart = new ShoppingCart();
        } else {
            StoreLogger.logError("Cannot logout - user is not logged in\n");

        }
    }

    public void deleteAccount(){
        StoreLogger.logStatus("Deleting account...");
        if (user != null){
            dao.deleteUzivatel(user.getJmeno());
            user = null;
            StoreLogger.logUpdate("Account deleted\n");
            this.cart = new ShoppingCart();
        } else {
            StoreLogger.logError("Cannot delete account - user is not logged in\n");
        }
    }

    public void addProductToFavourites(int produktId){
        StoreLogger.logStatus("Adding to favourite products...");

        if (user == null){
            StoreLogger.logError("To add products to favourite products, please log in to your account.\n");
            return;
        }

        Produkt p = dao.getProdukt(produktId);
        dao.addProductToFavourites(user, p);
        StoreLogger.logUpdate("Added " + p.getNazev() + " to favourite products\n");
    }

    public void printFavouriteProducts(){
        StoreLogger.logStatus("Printing favourite products...");

        if (user == null){
            StoreLogger.logError("To see your favourite products, please log in to your account.\n");
            return;
        }

        for (Produkt p: user.getFavoriteProducts()) {
            StoreLogger.logConfig("- " + p.getNazev());
        }
        StoreLogger.logConfig("");
    }

    public void printUsersWithFavouriteProduct(int productId){

        Produkt p = dao.getProdukt(productId);
        StoreLogger.logStatus("Printing users with " + p.getNazev() + " marked as favourite...");

        for (Uzivatel u: p.getLikedBy()){
            StoreLogger.logConfig("- " + u.getJmeno());
        }
        StoreLogger.logConfig("");
    }

    public void changeProductPrice(int produktId, BigDecimal newPrice){
        StoreLogger.logStatus("Changing price of product...");
        if (user == null){
            StoreLogger.logError("To change the product price, please log in to your employee account.\n");
            return;
        }

        if (!canUserEditProduct(produktId)){
            StoreLogger.logError("Error - this account does not have permission to change the price\n");
            return;
        }

        dao.updateProduktCena(produktId, newPrice);
        StoreLogger.logUpdate("Changed price of " + dao.getProdukt(produktId).getNazev() + " to " + newPrice + "Kc\n");
    }

    public void setPriceADMIN(int produktId, float price){
        dao.updateProduktCena(produktId, new BigDecimal(price));
    }

    private boolean isUserEmployee(){
        ArrayList<Zamestnanec> z = (ArrayList<Zamestnanec>) dao.getEmployees();
        for (Zamestnanec zz : z){
            if (Objects.equals(zz.getId(), user.getId())){
                return true;
            }
        }

        return false;
    }

    public Produkt getProdukt(int produktId){
        return dao.getProdukt(produktId);
    }

    private boolean canUserEditProduct(int productId){
        if (!isUserEmployee()){
            return false;
        }

        Zamestnanec z = dao.getZamestnanec(user.getId());
        for (Produkt p: z.getManagedProducts()){
            if (p.getId() == productId){
                return true;
            }
        }

        return false;
    }

    public void givePermissionToEditADMIN(int produktId, int zamestnanecId){
        Zamestnanec z = dao.getZamestnanec(zamestnanecId);
        z.getManagedProducts().add(dao.getProdukt(produktId));

        StoreLogger.logStatus("Gave permission to " + z.getUzivatel().getJmeno() + " to edit " + dao.getProdukt(produktId).getNazev());
    }

    public void takeAwayPermissionToEditADMIN(int produktId, int zamestnanecId){
        Zamestnanec z = dao.getZamestnanec(zamestnanecId);

        Produkt p = dao.getProdukt(produktId);
        z.getManagedProducts().remove(p);

        StoreLogger.logStatus("Took away permission from " + z.getUzivatel().getJmeno() + " to edit " + p.getNazev());
    }


    public void deleteAccountADMIN(String username){
        dao.deleteUzivatel(username);
    }

    public void register(String username, String password){
        StoreLogger.logStatus("Creating account...");

        try{
            dao.createUzivatel(username, password);
            StoreLogger.logUpdate("Registration successful\n");
        } catch (Exception e){
            StoreLogger.logError("User " + username + " already exists\n");
        }
    }


    public ArrayList<Produkt> produktSearch(String search, int limit){
        StoreLogger.logStatus("Searching in products for: " + search);

        String newSearch = search.replaceAll(" ", "%");
        newSearch = new String("%"+newSearch+"%");

        ArrayList<Produkt> produkts = (ArrayList<Produkt>) dao.searchForProdukt(newSearch);

        int resultSize = Math.min(produkts.size(), limit);
        StoreLogger.logConfig("Found: " + produkts.size() + " products (showing " + resultSize + ")");

        int counter = 1;
        for (Produkt p: produkts){
            //System.out.println(p.getNazev() + " " + p.getCena());
            StoreLogger.logConfig(counter + ". " + p.getNazev() + ", cena: " + p.getCena() + ", id: " + p.getId());
            counter++;
            if (counter > limit){
                break;
            }
        }
        StoreLogger.logStatus("");
        return produkts;
    }


    public void addProduktToCart(int produktId, int pieces){
        if (user == null){
            StoreLogger.logError("To add products to your shopping cart, please log in to your account.\n");
            return;
        }


        StoreLogger.logStatus("Adding product to cart...");
        Produkt p = dao.getProdukt(produktId);

        if (!checkProductAvaliability(produktId, pieces)){
            StoreLogger.logError("Cannot add " + p.getNazev() + " to cart - not enough pieces in stock\n");
            return;
        };

        cart.addProduct(p, pieces);
    }

    public void removeProduktFromCart(int produktId){
        StoreLogger.logStatus("Removing product from cart...");
        cart.removeProduct(dao.getProdukt(produktId));
    }


    public ArrayList<Pair<Sklad, Integer>> getProduktStock(int produktId){
        ArrayList<Pair<Sklad, Integer>> stock = new ArrayList<>();

        ArrayList<Jeulozen> jeulozen = (ArrayList<Jeulozen>) dao.getJeulozen(produktId);
        for (Jeulozen j: jeulozen){
            stock.add(new Pair<>(j.getIdSklad(), j.getKs()));
        }

        return stock;
    }

    private boolean checkProductAvaliability(int produktId, int pieces){
        StoreLogger.logStatus("Checking availability of produkt: " + dao.getProdukt(produktId).getNazev() + " (" + pieces + " ks)");

        ArrayList<Pair<Sklad, Integer>> stock = this.getProduktStock(produktId);
        for (Pair<Sklad, Integer> p: stock){
            if (p.getValue() >= pieces){
                StoreLogger.logStatus("Product is avaliable at " + p.getKey().getLokace() + " (" + p.getValue() + " ks)");
                return true;
            }
        }

        StoreLogger.logStatus("Product is not avaliable");
        return false;
    }

    private Sklad findSkladWithProduct(int produktId, int pieces){
        StoreLogger.logStatus("Checking availability of produkt: " + dao.getProdukt(produktId).getNazev() + " (" + pieces + " ks)");

        ArrayList<Pair<Sklad, Integer>> stock = this.getProduktStock(produktId);
        for (Pair<Sklad, Integer> p: stock){
            if (p.getValue() >= pieces){
                StoreLogger.logStatus("Product is avaliable");
                return p.getKey();
            }
        }

        StoreLogger.logStatus("Product is not avaliable");
        return null;
    }

    public Produkt updateProduktDescription(int id_produkt, String updatedDescription){
        Produkt p = dao.updateProduktPopis(id_produkt,updatedDescription);
        StoreLogger.logUpdate("Succesfully updated description of " + p.getNazev());
        return p;
    }


    public Produkt createProdukt(String nazev, String model, String vyrobce, String popis, BigDecimal cena){
        Produkt p = dao.createProdukt(nazev, model, vyrobce, popis, cena);
        StoreLogger.logUpdate("Succesfully created a new produkt: " + p.getNazev());
        return p;
    }


    //for demonstration purposes set quantity of product in all sklads to ks
    public void updateProductStock(int productId, int ks){
        ArrayList<Sklad> sklady = (ArrayList<Sklad>) dao.getAllSklad();
        for (Sklad s: sklady){
            dao.updateStockOfProduct(productId, s.getId(), ks);
        }
    }


    private LocalTime getCurrentTime() throws SQLException {
        String time = dao.getTime();
        int dotIndex = time.indexOf('.');
        time = time.substring(0, dotIndex);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime ltime = LocalTime.parse(time, timeFormatter);
        return ltime;
    }

    private LocalDate getCurrentDate() throws SQLException {
        String date = dao.getDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate ldate = LocalDate.parse(date, dateFormatter);
        return ldate;
    }


    public BigDecimal createOrder(){
        if (user == null){
            StoreLogger.logError("To add create an order, please log in to your account.\n");
            return new BigDecimal(0);
        }

        if (cart.getShoppingCart().isEmpty()){
            StoreLogger.logError("Cannot create order - shopping cart is empty\n");
            return new BigDecimal(0);
        }

        try{
            dao.beginTransaction();

            LocalDate orderDate = getCurrentDate();
            LocalTime orderTime = getCurrentTime();

            Objednavka o = dao.createObjednavkaNoTransaction(user, orderDate, orderTime);
            StoreLogger.logStatus("Creating order " + o.getId() + "...");

            for (Pair<Produkt, Integer> pair : cart.getShoppingCart()){
                Produkt p = pair.getKey();
                Integer pieces = pair.getValue();

                StoreLogger.logStatus("Adding " + p.getNazev() + " to order");

                Polozka polozka = dao.createPolozkaNoTransaction(p.getId(), o.getId(), pieces, p.getCena());
                Sklad sklad = findSkladWithProduct(polozka.getIdProdukt().getId(), pieces);

                if (sklad == null){

                    StoreLogger.logError("Error creating order " + o.getId());
                    StoreLogger.logError("Not enough pieces of " + p.getNazev() + " in stock");
                    StoreLogger.logError("Order cancelled\n");

                    dao.rollbackTransaction();
                    return new BigDecimal(0);
                }

                Jeulozen j = dao.removeKsFromSklad(p.getId(), sklad.getId(), pieces);
                if (j == null){
                    StoreLogger.logError("Error creating order " + o.getId());
                    StoreLogger.logError("Not enough pieces of " + p.getNazev() + " at sklad: " + sklad.getLokace());
                    StoreLogger.logError("Order cancelled\n");

                    dao.rollbackTransaction();
                    return new BigDecimal(0);
                }

                StoreLogger.logUpdate("Added to order: "
                        + "produkt: " + polozka.getIdProdukt().getNazev() + ", "
                        + "cena: " + polozka.getId().getCena() + ", "
                        + "ks: " + polozka.getId().getKs() + "\n");
            }

            dao.commitTransaction();

            StoreLogger.logSuccess("Successfully created order : order id = " + o.getId() + "\n");
            BigDecimal totalPrice = getOrderSummaryNew(o.getId());
            return totalPrice;

        } catch (SQLException e){
            StoreLogger.logError("Error creating order!");
        } catch (Exception e){
            StoreLogger.logError("Error creating order!");
            StoreLogger.logError(e.getMessage());
        }

        return new BigDecimal(0);
    }

    public BigDecimal getOrderSummaryNew(int orderId){
        StoreLogger.logStatus("Printing order summary...");
        StoreLogger.logConfig("Order " + orderId + " summary");
        ArrayList<Polozka> polozky = (ArrayList<Polozka>) dao.getPolozkaFromOrder(orderId);
        BigDecimal total_price = new BigDecimal(0);
        int counter = 1;
        for (Polozka polozka : polozky){

            BigDecimal polozkaPrice = polozka.getId().getCena().multiply(new BigDecimal(polozka.getId().getKs()));
            total_price = total_price.add(polozkaPrice);

            StringBuilder str = new StringBuilder();
            str.append(counter)
                    .append(". ")
                    .append(polozka.getIdProdukt().getNazev())
                    .append(", ")
                    .append(polozka.getId().getKs())
                    .append(" ks, ")
                    .append(polozkaPrice)
                    .append(" Kc");
            StoreLogger.logConfig(str.toString());

            counter++;
        }
        StoreLogger.logConfig("Total price: " + total_price + " Kc\n");
        return total_price;
    }

    public void printShoppingCart(){
        StoreLogger.logStatus("Printing shopping cart...");
        StoreLogger.logConfig(cart.toString());

    }
}
