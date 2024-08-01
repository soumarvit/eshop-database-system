package cvut.fel.store;

import java.math.BigDecimal;

public class Main {


    //id 7 = dell monitor from search 1
    //id 4 = macbook from search 2
    //id 3 = rtx 4060 from seatch 3
    //hard coded for demonstration purposes
    static int DELL_MONITOR_ID = 7;
    static int MACBOOK_ID = 4;
    static int RTX_4060_ID = 3;

    static String adminUsername = "Admin";
    static String adminPassword = "Admin";
    static int adminID = 33;

    static String normalUserUsername = "User";
    static String normalUserPassword = "User";
    static int normalUserId = 58;


    public static void main(String[] args) {
        System.out.println("online store example application");

        StoreApi store = new StoreApi();

        userExample1(store);
        userExample2(store);
        userExample3(store);
        employeeExample1(store);
    }

    /**
     *
     * Example of overall application usage
     * 1) login
     * 2) product search
     * 3) add products to cart
     * 4) create order
     *
     */
    private static void userExample1(StoreApi store){
        printline();
        try{
            store.startConnection();

            /////////////////////////////////////////////
            //set product stock for demonstration purposes
            store.updateProductStock(DELL_MONITOR_ID, 10);
            store.updateProductStock(MACBOOK_ID, 10);
            store.updateProductStock(RTX_4060_ID, 0);
            /////////////////////////////////////////////


            StoreLogger.logStatus("RUNNING USER EXAMPLE 1");

            store.login(adminUsername, adminPassword);

            store.produktSearch("Dell monitor", 5);
            store.produktSearch("MacBook", 2);
            store.produktSearch("RTX 4060", 2);

            store.addProduktToCart(DELL_MONITOR_ID, 2);
            store.addProduktToCart(MACBOOK_ID, 1);
            store.addProduktToCart(RTX_4060_ID, 1);

            store.printShoppingCart();
            store.createOrder();

            store.logout();

            store.endConnection();

        }catch (Exception e){
            StoreLogger.logError(e.getMessage());
        }
        printline();
    }


    /**
     *
     * Example of transaction - creating an order while the products are no longer in stock
     * 1) login
     * 2) add products to cart - enough in stock -> OK
     * 3) simulate the product getting purchased by someone else - not in stock but in cart
     * 4) create order -> fail because product not in stock
     * 5) remove the product from cart
     * 6) create order -> now OK
     *
     */
    private static void userExample2(StoreApi store){
        printline();
        try{
            store.startConnection();

            /////////////////////////////////////////////
            //set product stock for demonstration purposes
            store.updateProductStock(DELL_MONITOR_ID, 10);
            store.updateProductStock(MACBOOK_ID, 10);
            /////////////////////////////////////////////


            StoreLogger.logStatus("RUNNING USER EXAMPLE 2");

            store.login(adminUsername, adminPassword);

            //add products to cart - it will be okay because there is enough pieces in stock
            store.addProduktToCart(DELL_MONITOR_ID, 2);
            store.addProduktToCart(MACBOOK_ID, 1);

            store.printShoppingCart();

            //simulate that other user ordered all remaining macbooks
            //stock in every sklad = 0 ks
            store.updateProductStock(MACBOOK_ID, 0);

            //now try creating the order
            //should not come through
            store.createOrder();

            //remove macbook from cart and create order again - should come through
            store.removeProduktFromCart(MACBOOK_ID);
            store.printShoppingCart();
            store.createOrder();

            store.logout();

            store.endConnection();

        }catch (Exception e){
            StoreLogger.logError(e.getMessage());
        }
        printline();
    }


    /**
     *
     * Example of account usage + many to many
     * 1) try adding product to favourites -> FAIL because user not logged in
     * 2) login
     * 3) user does not exist
     * 4) register new account and login
     * 5) try adding product to favourites -> now OK
     * 6) printing favourite products of user             --manyToMany
     * 7) printing users that marked product as favourite --manyToMany
     */
    private static void userExample3(StoreApi store){
        printline();
        try{
            store.startConnection();


            StoreLogger.logStatus("RUNNING USER EXAMPLE 3");

            String username = "Uzivatel123";
            String password = "heslo123";

            /////////////////////////////////////////////
            //delete account for example purposes
            store.deleteAccountADMIN(username);
            /////////////////////////////////////////////

            store.addProductToFavourites(MACBOOK_ID);

            //account does not exist -> this will fail
            store.login(username, password);

            store.register(username, password);
            store.login(username, password);

            store.addProductToFavourites(MACBOOK_ID);
            store.addProductToFavourites(RTX_4060_ID);

            store.printFavouriteProducts();
            store.printUsersWithFavouriteProduct(MACBOOK_ID);
            StoreLogger.logStatus("(Current user: " + username + ")");

            store.logout();

            store.endConnection();

        }catch (Exception e){
            StoreLogger.logError(e.getMessage());
        }
        printline();
    }

    /**
     * Example of employee has more permission than normal user + inheritance
     * 1) login as normal user - no permissions to edit
     * 2) change product price -> FAIL
     * 3) login as employee - without permission for this specific product
     * 4) change product price -> FAIL
     * 5) give the employee the permission to edit this specific product
     * 6) change product price -> OK
     */
    private static void employeeExample1(StoreApi store){
        printline();
        try{
            store.startConnection();

            /////////////////////////////////////////////
            //remove edit permission for admin for example purposes
            store.takeAwayPermissionToEditADMIN(MACBOOK_ID, adminID);

            //set price to 123 for demonstration purposes
            store.setPriceADMIN(MACBOOK_ID, 123);
            /////////////////////////////////////////////


            StoreLogger.logStatus("RUNNING EMPLOYEE EXAMPLE 1");

            BigDecimal oldPrice = store.getProdukt(MACBOOK_ID).getCena();
            BigDecimal newPrice = new BigDecimal("37900.90");

            //login as normal user
            store.login(normalUserUsername, normalUserPassword);
            StoreLogger.logStatus("(Logged in as " + normalUserUsername + ")");

            //logged in as normal user -> fail
            store.changeProductPrice(MACBOOK_ID, newPrice);
            store.logout();


            //login as admin without permission to edit this product
            store.login(adminUsername, adminPassword);
            StoreLogger.logStatus("(Logged in as " + adminUsername + ")");

            //no permission -> fail
            store.changeProductPrice(MACBOOK_ID, newPrice);


            //give employee the permission to edit the product for demonstration purposes
            store.givePermissionToEditADMIN(MACBOOK_ID, adminID);
            StoreLogger.logStatus("(Logged in as " + adminUsername + " with added permission)");
            //employee now has permissions -> OK price changed
            store.changeProductPrice(MACBOOK_ID, newPrice);

            //print old price vs new price
            BigDecimal updatedPrice = store.getProdukt(MACBOOK_ID).getCena();
            StoreLogger.logConfig("old price: " + oldPrice);
            StoreLogger.logConfig("new price: " + updatedPrice);

            store.logout();

            store.endConnection();

        }catch (Exception e){
            StoreLogger.logError(e.getMessage());
        }
        printline();
    }

    private static void printline(){
        StoreLogger.logStatus("-------------------------------------------------------------------------------------");
    }



}