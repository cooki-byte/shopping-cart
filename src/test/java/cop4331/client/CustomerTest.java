package cop4331.client;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

public class CustomerTest {

    private Customer customer;
    private Cart cart;
    private Database databaseMock;

    @Before
    public void setUp() {
        cart = new Cart();
        customer = new Customer("cust1", "customer", "password", cart);
        databaseMock = mock(Database.class);
        Database.setInstance(databaseMock);
    }

    @Test
    public void testCheckoutWithEmptyCart() {
        customer.checkout();
        verify(databaseMock, never()).updateProduct(any(Product.class));
        verify(databaseMock, never()).updateUser(any(Seller.class));
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testCheckoutWithProducts() {
        Product product = new Product("1", "Laptop", "A high-performance laptop", 999.99, 10, "sell1", 800.00);
        cart.addItem(product, 2);

        Seller seller = new Seller("sell1", "seller", "password");
        when(databaseMock.getSellerById("sell1")).thenReturn(seller);

        customer.checkout();

        assertEquals(8, product.getQuantity());
        verify(databaseMock, times(1)).updateProduct(product);
        verify(databaseMock, times(1)).updateUser(seller);
        verify(databaseMock, times(1)).updateUser(customer);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void testCheckoutUpdatesSellerFinancialData() {
        Product product = new Product("1", "Laptop", "A high-performance laptop", 999.99, 10, "sell1", 800.00);
        cart.addItem(product, 2);

        Seller seller = new Seller("sell1", "seller", "password");
        when(databaseMock.getSellerById("sell1")).thenReturn(seller);

        FinancialData financialData = seller.getFinancialData();
        financialData.updateData(0.0, 90.00);

        customer.checkout();

        assertEquals(8, product.getQuantity());
        verify(databaseMock, times(1)).updateProduct(product);
        verify(databaseMock, times(1)).updateUser(seller);
        verify(databaseMock, times(1)).updateUser(customer);
        assertTrue(cart.getItems().isEmpty());

        assertEquals(1999.98, financialData.getRevenues(), 0.01);
        assertEquals(90.00, financialData.getCosts(), 0.01); // Costs should remain the same
        assertEquals(1909.98, financialData.getProfits(), 0.01); // Profits should be updated
    }
}