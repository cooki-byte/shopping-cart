package cop4331.client;

import java.util.List;
import java.util.ArrayList;

/**
 * The Cart class represents a shopping cart that contains line items and notifies observers of changes.
 */
public class Cart {
    private List<LineItem> items;
    private List<Observer> observers; // List of observers
    private double total; // Add the total property

    /**
     * Constructs an empty Cart.
     */
    public Cart() {
        this.items = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.total = 0.0; // Initialize total to zero
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
     * Also updates the total cost.
     * 
     * @param items the new list of line items
     */
    public void setItems(List<LineItem> items) {
        this.items = items;
        calculateTotal(); // Update total when items are set
        notifyObservers(); // Notify observers when items are replaced
    }

    /**
     * Adds a line item to the cart and notifies observers.
     * Also updates the total cost.
     * 
     * @param product the product to add
     * @param qty the quantity of the product
     */
    public void addItem(Product product, int qty) {
        items.add(new LineItem(product, qty));
        total += product.getPrice() * qty; // Update total
        notifyObservers();
    }

    /**
     * Removes a line item from the cart and notifies observers.
     * Also updates the total cost.
     * 
     * @param product the product to remove
     */
    public void removeItem(Product product) {
        items.removeIf(item -> {
            if (item.getProduct().equals(product)) {
                total -= item.getProduct().getPrice() * item.getQuantity(); // Update total
                return true;
            }
            return false;
        });
        notifyObservers();
    }

    /**
     * Returns the total cost of the items in the cart.
     * 
     * @return the total cost
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total cost of the cart.
     * 
     * @param total the total cost to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Recalculates the total cost based on the items in the cart.
     */
    public void calculateTotal() {
        total = items.stream()
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
