package cop4331.client;

/**
 * The Observer interface should be implemented by any class that wants to be notified of changes
 * in the observable object.
 *
 * @param <T> the type of the observable object
 */
public interface Observer<T> {
    void update(T observable);
}
