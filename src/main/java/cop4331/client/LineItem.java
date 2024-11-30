package cop4331.client;

/**
 * Represents a line item in a cart or inventory, consisting of a product and its associated quantity.
 */
public class LineItem {
    /** The product associated with this line item. */
    private Product product;

    /** The quantity of the product in this line item. */
    private int quantity;

    /**
     * Default constructor for creating an empty line item.
     */
    public LineItem() {}

    /**
     * Constructs a line item with a specified product and quantity.
     *
     * @param product the product associated with this line item.
     * @param quantity the quantity of the product.
     */
    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Retrieves the product associated with this line item.
     *
     * @return the product in this line item.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with this line item.
     *
     * @param product the product to set.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Retrieves the quantity of the product in this line item.
     *
     * @return the quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in this line item.
     *
     * @param quantity the quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
