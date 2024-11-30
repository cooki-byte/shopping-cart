package cop4331.client;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Prevent total from being serialized

/**
 * The Cart class represents a shopping cart that contains line items and notifies observers of changes.
 */
public class Cart {
    private List<LineItem> items;
    private List<Observer> observers; // List of observers

    /**
     * Constructs an empty Cart.
     */
    public Cart() {
        this.items = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Returns the list of line items in the cart.
     * 
     * @return the list of line items
     */
    public List<LineItem> getItems() {
        return items;
    }

    /**
     * Sets the list of line items in the cart and notifies observers.
     * 
     * @param items the new list of line items
     */
    public void setItems(List<LineItem> items) {
        this.items = items;
        notifyObservers(); // Notify observers when items are replaced
    }

    /**
     * Adds a line item to the cart and notifies observers.
     * 
     * @param product the product to add
     * @param qty the quantity of the product
     */
    public void addItem(Product product, int qty) {
        items.add(new LineItem(product, qty));
        notifyObservers();
    }

    /**
     * Removes a line item from the cart and notifies observers.
     * 
     * @param product the product to remove
     */
    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
        notifyObservers();
    }

    /**
     * Returns the total cost of the items in the cart.
     * 
     * @return the total cost
     */
    @JsonIgnore
    public double getTotal() {
        return items.stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
    }

    /**
     * Add an observer.
     * 
     * @param observer the observer to add
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer.
     * 
     * @param observer the observer to remove
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of changes to the cart.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}