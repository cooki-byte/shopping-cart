package cop4331.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a product bundle that consists of multiple products.
 * Applies a 10% discount to the total price of the bundled products.
 */
public class ProductBundle extends Product {
    private List<Product> products;

    public ProductBundle() {
        super();
        this.products = new ArrayList<>();
    }

    public ProductBundle(String id, String name, String description) {
        super(id, name, description, 0.0, 0, "", 0.0, "bundle");
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        // Update sellerId if necessary
        if (this.getSellerId() == null || this.getSellerId().isEmpty()) {
            this.setSellerId(product.getSellerId());
        }
        // Recalculate price and quantity
        this.setPrice(getPrice());
        this.setQuantity(getQuantity());
    }

    @Override
    public double getPrice() {
        double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();
        return totalPrice * 0.9; // Apply 10% discount
    }

    @Override
    public int getQuantity() {
        return products.stream().mapToInt(Product::getQuantity).min().orElse(0);
    }

    @Override
    public void setQuantity(int quantity) {
        for (Product product : products) {
            product.setQuantity(quantity);
        }
    }

    @Override
    public String getDescription() {
        StringBuilder description = new StringBuilder(super.getDescription() + "\nIncludes:\n");
        for (Product product : products) {
            description.append("- ").append(product.getName()).append("\n");
        }
        return description.toString();
    }
}
