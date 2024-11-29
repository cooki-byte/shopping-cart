package cop4331.client;

import java.util.List;
import java.util.ArrayList;

public class Cart {
    private List<LineItem> items;
    private List<Observer> observers; // List of observers

    public Cart() {
        this.items = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
        notifyObservers(); // Notify observers when items are replaced
    }

    public void addItem(Product product, int qty) {
        items.add(new LineItem(product, qty));
        notifyObservers();
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
        notifyObservers();
    }

    public double getTotal() {
        return items.stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
    }

    // Add an observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Remove an observer
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Notify all observers about a change
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}