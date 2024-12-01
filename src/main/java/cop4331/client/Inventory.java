package cop4331.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents an inventory of products, allowing for management of product lists 
 * and notification of changes to observers.
 */
public class Inventory implements Iterable<Product> {
    /** The list of products in the inventory. */
    private List<Product> products;

    /** The list of observers monitoring the inventory. */
    private transient List<Observer<Inventory>> observers;

    /**
     * Constructs an empty Inventory with no products or observers.
     */
    public Inventory() {
        this.products = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Retrieves an unmodifiable list of products in the inventory.
     *
     * @return the unmodifiable list of products.
     */
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    /**
     * Sets the list of products in the inventory and notifies all observers of the change.
     *
     * @param products the new list of products to set.
     */
    public void setProducts(List<Product> products) {
        this.products = new ArrayList<>(products);
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
     * Finds a product by its ID.
     *
     * @param productId the ID of the product to find.
     * @return the product with the matching ID, or null if not found.
     */
    public Product findProductById(String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Updates an existing product in the inventory.
     *
     * @param updatedProduct the product with updated information.
     */
    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(updatedProduct.getId())) {
                products.set(i, updatedProduct);
                notifyObservers();
                return;
            }
        }
        // Optionally, throw an exception if product not found
    }

    /**
     * Provides an iterator for the products in the inventory.
     *
     * @return an iterator over the products.
     */
    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }

    /**
     * Adds an observer to the inventory. Observers are notified whenever the inventory changes.
     *
     * @param observer the observer to add.
     */
    public void addObserver(Observer<Inventory> observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the inventory.
     *
     * @param observer the observer to remove.
     */
    public void removeObserver(Observer<Inventory> observer) {
        observers.remove(observer);
    }


    /**
     * Notifies all observers of a change to the inventory.
     */
    public void notifyObservers() {
        for (Observer<Inventory> observer : observers) {
            observer.update(this);
        }
    }
}
