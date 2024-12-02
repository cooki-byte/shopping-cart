package cop4331.client;

/**
 * Represents a discounted product that wraps another product and applies a discount.
 */
public class DiscountedProduct extends Product {
    private Product product;
    private double discountRate;

    public DiscountedProduct() {
        super();
    }

    public DiscountedProduct(Product product, double discountRate) {
        super(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getSellerId(), product.getInvoicePrice(), "discountedProduct");
        this.product = product;
        this.discountRate = discountRate;
    }

    @Override
    public double getPrice() {
        return product.getPrice() * (1 - discountRate);
    }

    @Override
    public int getQuantity() {
        return product.getQuantity();
    }

    @Override
    public void setQuantity(int quantity) {
        product.setQuantity(quantity);
    }

    @Override
    public String getName() {
        return product.getName() + " (Discounted)";
    }

    @Override
    public String getDescription() {
        return product.getDescription() + "\nDiscount: " + (discountRate * 100) + "% off";
    }
}
