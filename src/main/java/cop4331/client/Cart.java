package cop4331.client;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * The Cart class represents a shopping cart that contains line items and notifies observers of changes.
 */
public class Cart implements Iterable<LineItem> {
    private List<LineItem> items;
    private transient List<Observer<Cart>> observers;
    private double total;
    private boolean empty;

    /**
     * Constructs an empty Cart.
     */
    public Cart() {
        this.items = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.total = 0.0;
        this.empty = true;
    }

    /**
     * Returns an unmodifiable list of line items in the cart.
     * 
     * @return the unmodifiable list of line items
     */
    public List<LineItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Sets the list of line items in the cart and notifies observers.
     * Also updates the total cost and empty status.
     * 
     * @param items the new list of line items
     */
    public void setItems(List<LineItem> items) {
        this.items = new ArrayList<>(items);
        calculateTotal();
        empty = this.items.isEmpty();
        notifyObservers();
    }

    /**
     * Adds a line item to the cart and notifies observers.
     * Also updates the total cost and empty status.
     * 
     * @param product the product to add
     * @param qty the quantity of the product
     */
    public void addItem(Product product, int qty) {
        items.add(new LineItem(product, qty));
        calculateTotal();
        empty = items.isEmpty();
        notifyObservers();
    }

    /**
     * Removes a line item from the cart and notifies observers.
     * Also updates the total cost and empty status.
     * 
     * @param product the product to remove
     */
    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
        calculateTotal();
        empty = items.isEmpty();
        notifyObservers();
    }
    /**
     * Handles quantity updates and notifies observers.
     * 
     * @param product
     * @param newQuantity
     */
    public void updateItemQuantity(Product product, int newQuantity) {
        for (LineItem item : items) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(newQuantity);
                calculateTotal();
                notifyObservers(); // Notify observers about the change
                return;
            }
        }
    }

    /**
     * Clears all items from the cart and notifies observers.
     */
    public void clearCart() {
        items.clear();
        calculateTotal();
        empty = true;
        notifyObservers();
    }

    /**
     * Checks if the cart is empty.
     * 
     * @return true if the cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Sets the empty status of the cart.
     * 
     * @param empty the empty status to set
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
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
     * Also updates the empty status.
     */
    public void calculateTotal() {
        total = items.stream()
                     .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                     .sum();
        empty = items.isEmpty();
    }

    /**
     * Adds an observer.
     * 
     * @param observer the observer to add
     */
    public void addObserver(Observer<Cart> observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer.
     * 
     * @param observer the observer to remove
     */
    public void removeObserver(Observer<Cart> observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of changes to the cart.
     */
    public void notifyObservers() {
        for (Observer<Cart> observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Provides an iterator over the line items in the cart.
     * 
     * @return an iterator over the line items
     */
    @Override
    public Iterator<LineItem> iterator() {
        return items.iterator();
    }
}
