package cop4331.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Database class represents a singleton database that stores users and products.
 * It uses JSON files for persistence.
 */
public class Database {
    private static Database instance; // Singleton instance
    private List<User> users;         // List of users
    private List<Product> products;   // List of products
    private ObjectMapper objectMapper; // For JSON serialization/deserialization

    private static final String USERS_FILE = "users.json";
    private static final String PRODUCTS_FILE = "products.json";

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the lists and loads data from JSON files.
     */
    private Database() {
        this.users = new ArrayList<>();
        this.products = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        loadData(); // Load data on initialization
    }

    /**
     * Gets the Singleton instance of Database.
     * 
     * @return the singleton instance
     */
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Loads data from JSON files into the users and products lists.
     */
    private void loadData() {
        try {
            users = objectMapper.readValue(new File(USERS_FILE), new TypeReference<List<User>>() {});
            products = objectMapper.readValue(new File(PRODUCTS_FILE), new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading data.");
        }
    }

    /**
     * Saves data from the users and products lists to JSON files.
     */
    public void saveData() {
        try {
            objectMapper.writeValue(new File(USERS_FILE), users);
            objectMapper.writeValue(new File(PRODUCTS_FILE), products);

            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving data.");
        }
    }

    /**
     * Gets the list of users.
     * 
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Gets the list of products.
     * 
     * @return the list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Adds a user to the database and saves the data.
     * 
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
        saveData(); // Save changes
    }

    /**
     * Adds a product to the database and saves the data.
     * 
     * @param product the product to add
     */
    public void addProduct(Product product) {
        products.add(product);
        saveData(); // Save changes
    }
}
