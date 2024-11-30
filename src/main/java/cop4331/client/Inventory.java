package cop4331.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents an inventory of products, allowing for management of product lists 
 * and notification of changes to observers.
 */
public class Inventory {
    /** The list of products in the inventory. */
    private List<Product> products;

    /** The list of observers monitoring the inventory. */
    private List<Observer> observers;

    /**
     * Constructs an empty Inventory with no products or observers.
     */
    public Inventory() {
        this.products = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Retrieves the list of products in the inventory.
     *
     * @return the list of products.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products in the inventory and notifies all observers of the change.
     *
     * @param products the new list of products to set.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
        notifyObservers();
    }

    /**
     * Adds a product to the inventory and notifies all observers of the change.
     *
     * @param product the product to add to the inventory.
     */
    public void addProduct(Product product) {
        products.add(product);
        notifyObservers();
    }

    /**
     * Removes a product from the inventory and notifies all observers of the change.
     *
     * @param product the product to remove from the inventory.
     */
    public void removeProduct(Product product) {
        products.remove(product);
        notifyObservers();
    }

    /**
     * Provides an iterator for the products in the inventory.
     *
     * @return an iterator over the products.
     */
    public Iterator<Product> iterator() {
        return products.iterator();
    }

    /**
     * Adds an observer to the inventory. Observers are notified whenever the inventory changes.
     *
     * @param o the observer to add.
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Removes an observer from the inventory.
     *
     * @param o the observer to remove.
     */
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifies all observers of a change to the inventory.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
