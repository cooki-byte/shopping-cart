package cop4331.client;

/**
 * Interface representing an observer in the Observer design pattern.
 * Observers are notified of changes in the observable objects they are monitoring.
 */
public interface Observer {

    /**
     * Method called when the observable object notifies its observers of a change.
     *
     * @param observable the object that has been updated. This can be any type of object being observed.
     */
    void update(Object observable);
}
