package cop4331.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
     * 
     * @throws FileNotFoundException if files do not exist
     */
    public void loadData() {
        try {
            // Load users
            File usersFile = new File(USERS_FILE);
            if (usersFile.exists()) {
                users = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
            } else {
                // Throw exception if file not found
                throw new FileNotFoundException("Users file not found: " + USERS_FILE);
            }

            // Load products
            // File productsFile = new File(PRODUCTS_FILE);
            // if (productsFile.exists()) {
            //     products = objectMapper.readValue(productsFile, new TypeReference<List<Product>>() {});
            // } else {
            //     // Throw an exception if the file is not found
            //      throw new FileNotFoundException("Products file not found: " + PRODUCTS_FILE);
            // }
        } catch (IOException e){
            // Log the exception and exit program
            System.err.println("Critical error: " + e.getMessage());
            System.exit(1);
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
