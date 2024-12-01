package cop4331.client;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Abstract base class representing a user in the system.
 * A user can be a {@link Customer} or a {@link Seller}, with unique credentials.
 * Provides common properties and methods for all user types.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Customer.class, name = "customer"),
    @JsonSubTypes.Type(value = Seller.class, name = "seller")
})
public abstract class User {
    /** The unique identifier for the user. */
    protected String id;

    /** The username for the user. */
    protected String username;

    /** The password for the user. */
    protected String password;

    /**
     * The type of the user.
     */
    protected String type;

    /**
     * Default constructor required for Jackson deserialization.
     */
    public User() {}

    /**
     * Constructs a user with the specified ID, username, and password.
     *
     * @param id the unique identifier for the user.
     * @param username the username for the user.
     * @param password the password for the user.
     */
    public User(String id, String username, String password, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    /**
     * Authenticates the user by comparing the provided password with the stored password.
     *
     * @param password the password to authenticate.
     * @return true if the provided password matches the stored password, false otherwise.
     */
    public boolean login(String password) {
        return this.password.equals(password);
    }

    /**
     * Logs out the user and displays a logout message.
     */
    public void logout() {
        System.out.println(username + " logged out.");
    }

    /**
     * Retrieves the type of the user.
     *
     * @return the type of the user.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the user.
     *
     * @param type the type of the user.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the unique identifier for the user.
     *
     * @return the user ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param id the user ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the username for the user.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the user.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the password for the user.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
