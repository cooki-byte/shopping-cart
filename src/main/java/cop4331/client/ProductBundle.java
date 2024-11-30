package cop4331.client;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("bundle")
/**
 * Represents a bundle of products, allowing grouping multiple products as a single item.
 * Implements the Composite pattern by extending Product and containing multiple Product items.
 */
public class ProductBundle extends Product{
    private List<Product> products;

    /**
     * Default constructor for JSON deserialization.
     */
    public ProductBundle() {
        super();
        this.products = new ArrayList<>();
    }

    /**
     * Constructs a ProductBundle with a specified ID and name.
     *
     * @param id   the unique identifier of the bundle.
     * @param name the name of the bundle.
     */
    public ProductBundle(String id, String name) {
        super(id, name, "", 0.0, Integer.MAX_VALUE);
        this.products = new ArrayList<>();
        updatePriceAndQuantity();
    }

    /**
     * Adds a product to the bundle.
     *
     * @param product the product to add.
     */
    public void addProduct(Product product) {
        products.add(product);
        updatePriceAndQuantity();
    }

    /**
     * Removes a product from the bundle.
     *
     * @param product the product to remove.
     */
    public void removeProduct(Product product) {
        products.remove(product);
        updatePriceAndQuantity();
    }

    /**
     * Updates the price and quantity of the bundle based on its components.
     */
    private void updatePriceAndQuantity() {
        double totalPrice = 0.0;
        int minQuantity = Integer.MAX_VALUE;
        for (Product product : products) {
            totalPrice += product.getPrice();
            minQuantity = Math.min(minQuantity, product.getQuantity());
        }
        this.setPrice(totalPrice);
        this.setQuantity(minQuantity);
    }

    /**
     * Retrieves the list of products in the bundle.
     *
     * @return the list of products.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products in the bundle and updates price and quantity accordingly.
     *
     * @param products the list of products to set.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
        updatePriceAndQuantity();
    }
}
