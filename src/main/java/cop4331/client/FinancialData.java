package cop4331.client;

/**
 * The FinancialData class represents the financial data of a seller.
 * It includes costs, revenues, and profits.
 */
public class FinancialData {
    private double costs;
    private double revenues;
    private double profits;

    /**
     * Updates the financial data with a new sale and cost amount.
     * 
     * @param saleAmount the amount of the sale
     * @param costAmount the cost associated with the sale
     */
    public void updateData(double saleAmount, double costAmount) {
        this.revenues += saleAmount;
        this.costs += costAmount;
        this.profits = this.revenues - this.costs;
    }

    /**
     * Gets the current profits.
     * 
     * @return the current profits
     */
    public double getProfits() {
        return profits;
    }

    /**
     * Gets the current costs.
     * 
     * @return the current costs
     */
    public double getCosts() {
        return costs;
    }

    /**
     * Gets the current revenues.
     * 
     * @return the current revenues
     */
    public double getRevenues() {
        return revenues;
    }
}