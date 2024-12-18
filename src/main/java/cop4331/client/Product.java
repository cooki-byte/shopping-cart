package cop4331.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.io.Serializable;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY, // Change from PROPERTY to EXISTING_PROPERTY
    property = "type",
    visible = true // Ensures the 'type' property is available to the class
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Product.class, name = "product"),
    @JsonSubTypes.Type(value = ProductBundle.class, name = "bundle"),
    @JsonSubTypes.Type(value = DiscountedProduct.class, name = "discountedProduct")
})
/**
 * Represents a product in the system, including its details such as ID, name, description, price, and quantity.
 */
public class Product implements Serializable {
    /** The unique identifier of the product. */
    private String id;

    /** The name of the product. */
    private String name;

    /** A brief description of the product. */
    private String description;

    /** The price of the product. */
    private double price;

    /** The quantity of the product available in stock. */
    private int quantity;

    /** The unique identifier of the seller of the product. */
    private String sellerId;

    /** The invoice price of the product. */
    private double invoicePrice;

    private String type;

    /**
     * Default constructor for creating an empty product.
     */
    public Product() {}

    /**
     * Constructs a product with specified details.
     *
     * @param id the unique identifier of the product.
     * @param name the name of the product.
     * @param description a brief description of the product.
     * @param price the price of the product.
     * @param quantity the quantity of the product available in stock.
     * @param sellerId sellerId the unique identifier of the seller of the product.
     * @param invoicePrice the invoice price of the product.
     */
    public Product(String id, String name, String description, double price, int quantity, String sellerId, double invoicePrice, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sellerId = sellerId;
        this.invoicePrice = invoicePrice;
        this.type = type;
    }

    /**
     * Retrieves the unique identifier of the product.
     *
     * @return the product ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the product.
     *
     * @param id the product ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the product.
     *
     * @return the product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the type of the product.
     *
     * @return the type of the product.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the product.
     *
     * @param type the type of the product.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the product name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description of the product.
     *
     * @return the product description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     *
     * @param description the product description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the price of the product.
     *
     * @return the product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the product price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the quantity of the product available in stock.
     *
     * @return the product quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product available in stock.
     *
     * @param quantity the product quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the seller ID of the product.
     *
     * @return the seller ID.
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * Sets the seller ID of the product.
     *
     * @param sellerId the seller ID to set.
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * Retrieves the invoice price of the product.
     *
     * @return the invoice price.
     */
    public double getInvoicePrice() {
        return invoicePrice;
    }

    /**
     * Sets the invoice price of the product.
     *
     * @param invoicePrice the invoice price to set.
     */
    public void setInvoicePrice(double invoicePrice) {
        this.invoicePrice = invoicePrice;
    }
}
