package cop4331.client;

public interface Observer {
    void update(Object observable); // Generic Object to handle different types
}