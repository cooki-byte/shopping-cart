package cop4331.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance; // Singleton instance
    private List<User> users;         // List of users
    private List<Product> products;   // List of products
    private ObjectMapper objectMapper; // For JSON serialization/deserialization

    private static final String USERS_FILE = "users.json";
    private static final String PRODUCTS_FILE = "products.json";

    // Private constructor to enforce Singleton pattern
    private Database() {
        this.users = new ArrayList<>();
        this.products = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        loadData(); // Load data on initialization
    }

    // Get the Singleton instance of Database
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Load data from JSON files
    public void loadData() {
        try {
            // Load users
            File usersFile = new File(USERS_FILE);
            if (usersFile.exists()) {
                users = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
                System.out.println("Users loaded: " + users.size());
            } else {
                System.out.println("Users file not found. Initializing empty user list.");
                users = new ArrayList<>();
            }

            // Load products
            File productsFile = new File(PRODUCTS_FILE);
            if (productsFile.exists()) {
                products = objectMapper.readValue(productsFile, new TypeReference<List<Product>>() {});
                System.out.println("Products loaded: " + products.size());
            } else {
                System.out.println("Products file not found. Initializing empty product list.");
                products = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading data.");
        }
    }

    // Save data to JSON files
    public void saveData() {
        try {
            // Save users
            objectMapper.writeValue(new File(USERS_FILE), users);

            // Save products
            objectMapper.writeValue(new File(PRODUCTS_FILE), products);

            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving data.");
        }
    }

    // Get the list of users
    public List<User> getUsers() {
        return users;
    }

    // Get the list of products
    public List<Product> getProducts() {
        return products;
    }

    // Add a user to the database
    public void addUser(User user) {
        users.add(user);
        saveData(); // Save changes
    }

    // Add a product to the database
    public void addProduct(Product product) {
        products.add(product);
        saveData(); // Save changes
    }
}