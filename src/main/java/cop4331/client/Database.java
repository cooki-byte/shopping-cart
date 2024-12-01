package cop4331.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import java.nio.file.Files;
import java.nio.file.Paths;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class for managing persistent storage of users and products.
 */
public class Database {
    private static Database instance; // Singleton instance
    private List<User> users;         // List of users
    private List<Product> products;   // List of products
    private ObjectMapper objectMapper; // JSON serializer/deserializer

    private static final String USERS_FILE = "C:\\Users\\ftlsk\\OneDrive\\Desktop\\javaproject\\shopping-cart\\users.json";
    private static final String PRODUCTS_FILE = "C:\\Users\\ftlsk\\OneDrive\\Desktop\\javaproject\\shopping-cart\\products.json";

    // Private constructor for Singleton pattern
    private Database() {
        this.users = new ArrayList<>();
        this.products = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        loadData();
    }

    /**
     * Returns the Singleton instance of the Database.
     *
     * @return the Database instance.
     */
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Loads users and products from JSON files into memory.
     */
    public void loadData() {
        try {
            // Debugging users.json
        File usersFile = new File(USERS_FILE);
        if (usersFile.exists()) {
            System.out.println("Loading users.json...");
            System.out.println("JSON Content: " + new String(Files.readAllBytes(usersFile.toPath())));
            users = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
        } else {
            users = new ArrayList<>();
        }

        // Debugging products.json
        File productsFile = new File(PRODUCTS_FILE);
        if (productsFile.exists()) {
            System.out.println("Loading products.json...");
            System.out.println("JSON Content: " + new String(Files.readAllBytes(productsFile.toPath())));
            products = objectMapper.readValue(productsFile, new TypeReference<List<Product>>() {});
        } else {
            products = new ArrayList<>();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a product to the database and saves the data.
     *
     * @param product the product to add.
     */
    public void addProduct(Product product) {
        products.add(product);
        saveData(); // Save changes
    }

    /**
     * Saves users and products to their respective JSON files.
     */
    public void saveData() {
        try {
            objectMapper.writeValue(new File(USERS_FILE), users);
            objectMapper.writeValue(new File(PRODUCTS_FILE), products);

            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a product in the product list and saves to JSON.
     *
     * @param updatedProduct the product to update.
     */
    public void updateProduct(Product updatedProduct) {
        products.replaceAll(product -> product.getId().equals(updatedProduct.getId()) ? updatedProduct : product);
        saveData();
    }

    /**
     * Updates a user in the user list and saves to JSON.
     *
     * @param updatedUser the user to update.
     */
    public void updateUser(User updatedUser) {
        users.replaceAll(user -> user.getId().equals(updatedUser.getId()) ? updatedUser : user);
        saveData();
    }

    /**
     * Retrieves the list of users.
     *
     * @return the list of users.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Retrieves the list of products.
     *
     * @return the list of products.
     */
    public List<Product> getProducts() {
        return products;
    }
    
    /**
     * Finds a seller by their unique ID.
     *
     * @param sellerId the unique identifier of the seller.
     * @return the Seller object, or null if not found.
     */
    public Seller getSellerById(String sellerId) {
        for (User user : users) {
            if (user instanceof Seller && user.getId().equals(sellerId)) {
                return (Seller) user;
            }
        }
        return null;
    }
}
