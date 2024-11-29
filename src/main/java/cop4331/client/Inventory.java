package cop4331.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {
    private List<Product> products;
    private List<Observer> observers; // To track inventory observers

    public Inventory() {
        this.products = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // Get the list of products
    public List<Product> getProducts() {
        return products;
    }

    // Set the list of products (and notify observers)
    public void setProducts(List<Product> products) {
        this.products = products;
        notifyObservers();
    }

    // Add a product to the inventory
    public void addProduct(Product product) {
        products.add(product);
        notifyObservers(); // Notify observers of the change
    }

    // Remove a product from the inventory
    public void removeProduct(Product product) {
        products.remove(product);
        notifyObservers(); // Notify observers of the change
    }

    // Provide an iterator for the products
    public Iterator<Product> iterator() {
        return products.iterator();
    }

    // Add an observer
    public void addObserver(Observer o) {
        observers.add(o);
    }

    // Remove an observer
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    // Notify all observers about a change
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}