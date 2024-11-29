package cop4331.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class responsible for data persistence.
 * Manages the loading and saving of users and products to JSON files.
 * Implements the Singleton design pattern to ensure only one instance exists.
 */
public class Database {
    private static Database instance = null;
    private List<User> users;
    //private List<Product> products;
    private static final String USERS_FILE = "users.json";
    //private static final String PRODUCTS_FILE = "products.json";
    private ObjectMapper objectMapper;

    /**
     * Private constructor to prevent instantiation.
     * Initializes the users and products lists and loads data from files.
     */
    private Database() {
        users = new ArrayList<>();
        //products = new ArrayList<>();
        objectMapper = new ObjectMapper();
        loadData();
    }

    /**
     * Returns the singleton instance of the Database.
     *
     * @return the singleton Database instance
     */
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Loads users and products data from JSON files.
     * Initializes the lists if files do not exist.
     */
    public void loadData() {
        try {
            // Load users
            File usersFile = new File(USERS_FILE);
            if (usersFile.exists()) {
                users = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
            } else {
                // Initialize with default users
                users = new ArrayList<>();
                // Create a default customer
                Customer defaultCustomer = new Customer("cust1", "customer", "password");
                users.add(defaultCustomer);
                // Create a default seller
                Inventory inventory = new Inventory(); // Empty inventory
                Seller defaultSeller = new Seller("sell1", "seller", "password", inventory);
                users.add(defaultSeller);
                // Save the initial data
                saveData();
            }

            // Load products
            // File productsFile = new File(PRODUCTS_FILE);
            // if (productsFile.exists()) {
            //     products = objectMapper.readValue(productsFile, new TypeReference<List<Product>>() {});
            // } else {
            //     products = new ArrayList<>();
            // }
        } catch (Exception e) {
            e.printStackTrace();
            // Add exception handling
        }
    }

    /**
     * Saves users and products data to JSON files.
     */
    public void saveData() {
        try {
            // Save users
            objectMapper.writeValue(new File(USERS_FILE), users);

            // Save products
            //objectMapper.writeValue(new File(PRODUCTS_FILE), products);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    /**
     * Returns the list of users.
     *
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Returns the list of products.
     *
     * @return the list of products
     */
    // public List<Product> getProducts() {
    //     return products;
    // }

    // Additional methods
}
