package cop4331.client;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
   use = JsonTypeInfo.Id.NAME,
   include = JsonTypeInfo.As.PROPERTY,
   property = "type"
)
@JsonSubTypes({
   @JsonSubTypes.Type(value = Customer.class, name = "customer"),
   @JsonSubTypes.Type(value = Seller.class, name = "seller")
})

/**
 * Abstract class representing a user in the system.
 * Implemented by Customer and Seller classes.
 */
public abstract class User {
    protected String id;
    protected String username;
    protected String password;

    /**
     * Default constructor for Jackson deserialization.
     */
    public User() {}

    /**
     * Constructs a User with specified id, username, and password.
     *
     * @param id the user ID
     * @param username the username
     * @param password the password
     */
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Authenticates the user with the provided password.
     *
     * @param password the password to authenticate
     * @return true if authentication is successful, false otherwise
     */
    public abstract boolean login(String password);

    /**
     * Logs out the user from the system.
     */
    public abstract void logout();


    // Getters and Setters

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public String getId(){
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password){
        this.password = password;
    }
}
