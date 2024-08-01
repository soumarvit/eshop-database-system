package cvut.fel.store;

import cvut.fel.model.Produkt;
import javafx.util.Pair;

import java.util.ArrayList;

public class ShoppingCart {


    private ArrayList<Pair<Produkt, Integer>> shoppingCart;
    public ShoppingCart(){

        shoppingCart = new ArrayList<>();
    }

    public void addProduct(Produkt product, int quantity){
        shoppingCart.add(new Pair<>(product, quantity));
        StoreLogger.logUpdate("Added to shopping cart: " + product.getNazev() + ", ks: " + quantity + "\n");
    }

    public void removeProduct(Produkt product){
        int pid = product.getId();
        int counter = 0;
        for (Pair<Produkt, Integer> pair: shoppingCart){
            if (pair.getKey().getId() == pid){
                shoppingCart.remove(counter);
                StoreLogger.logUpdate("Removed from shopping cart: " + product.getNazev() + "\n");
                break;
            }
            counter++;
        }

    }

    public ArrayList<Pair<Produkt, Integer>> getShoppingCart(){
        return shoppingCart;
    }

    public void clearShoppingCart(){
        shoppingCart = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder shoppingCartString = new StringBuilder();
        int counter = 1;
        for(Pair<Produkt, Integer> pair : shoppingCart){
            shoppingCartString.append(counter).append(". ").append(pair.getKey().getNazev()).append(": ").append(pair.getValue()).append(" ks").append("\n");
            counter++;
        }

        return shoppingCartString.toString();
    }
}
