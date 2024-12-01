package cop4331.client;

/**
 * The Payment class handles payment processing.
 * Currently, it simulates payment processing and always returns true.
 */
public class Payment {
    /**
     * Processes the payment with the provided details.
     *
     * @param cardNumber the credit card number
     * @param expiration the expiration date in MM/YY format
     * @param cvv        the CVV code
     * @param amount     the amount to charge
     * @return true if payment is successful, false otherwise
     */
    public boolean processPayment(String cardNumber, String expiration, String cvv, double amount) {
        // Simulate payment processing logic
        // In a real application, integrate with a payment gateway
        return true;
    }
}
