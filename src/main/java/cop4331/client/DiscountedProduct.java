package cop4331.client;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("discountedProduct")
/**
 * Represents a product with a discount applied.
 * Implements the Decorator pattern by wrapping a Product object and modifying its price.
 */
public class DiscountedProduct extends Product{
    private Product originalProduct;
    private double discountRate;

    /**
     * Default constructor for JSON deserialization.
     */
    public DiscountedProduct() {
        super();
    }

    /**
     * Constructs a DiscountedProduct with a specified original product and discount rate.
     *
     * @param originalProduct the product to wrap.
     * @param discountRate    the discount rate to apply (e.g., 0.10 for 10% discount).
     */
    public DiscountedProduct(Product originalProduct, double discountRate) {
        super(originalProduct.getId(), originalProduct.getName(), originalProduct.getDescription(),
                originalProduct.getPrice(), originalProduct.getQuantity());
        this.originalProduct = originalProduct;
        this.discountRate = discountRate;
    }

    /**
     * Retrieves the price of the product after applying the discount.
     *
     * @return the discounted price.
     */
    @Override
    public double getPrice() {
        return originalProduct.getPrice() * (1 - discountRate);
    }

    /**
     * Retrieves the original product being decorated.
     *
     * @return the original product.
     */
    public Product getOriginalProduct() {
        return originalProduct;
    }

    /**
     * Sets the original product being decorated.
     *
     * @param originalProduct the original product.
     */
    public void setOriginalProduct(Product originalProduct) {
        this.originalProduct = originalProduct;
    }

    /**
     * Retrieves the discount rate.
     *
     * @return the discount rate.
     */
    public double getDiscountRate() {
        return discountRate;
    }

    /**
     * Sets the discount rate.
     *
     * @param discountRate the discount rate to set (e.g., 0.10 for 10% discount).
     */
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}
