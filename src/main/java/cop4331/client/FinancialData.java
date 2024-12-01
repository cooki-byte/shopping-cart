package cop4331.client;

import java.io.Serializable;

/**
 * The FinancialData class represents the financial data for a seller,
 * including revenues, costs, and profits.
 */
public class FinancialData implements Serializable {
    private double costs;
    private double revenues;
    private double profits;

    public FinancialData() {
        this.costs = 0.0;
        this.revenues = 0.0;
        this.profits = 0.0;
    }

    public double getCosts() {
        return costs;
    }

    public double getRevenues() {
        return revenues;
    }

    public double getProfits() {
        return profits;
    }

    /**
     * Updates the financial data with the given sale amount and cost amount.
     *
     * @param saleAmount the amount earned from the sale
     * @param costAmount the cost associated with the sale
     */
    public void updateData(double saleAmount, double costAmount) {
        this.revenues += saleAmount;
        this.costs += costAmount;
        calculateProfits();
    }

    private void calculateProfits() {
        this.profits = this.revenues - this.costs;
    }
}
