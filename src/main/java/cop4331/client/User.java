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
public abstract class User {
    protected String id;
    protected String username;
    protected String password;

    // Default constructor for Jackson deserialization
    public User() {}

    // Constructor with parameters
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Universal login logic
    public boolean login(String password) {
        return this.password.equals(password);
    }

    // Default logout logic
    public void logout() {
        System.out.println(username + " logged out.");
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
