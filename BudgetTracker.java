/**
 * Manages the user's remaining daily budget.
 */
public class BudgetTracker {

    // Dollars left to spend today
    private double remainingBudget;

    /** Stores the initial daily budget. */
    public void setBudget(double amount) {
        remainingBudget = amount;
    }

    /** Returns the current remaining budget. */
    public double getRemainingBudget() {
        return remainingBudget;
    }

    /** Returns true if the remaining budget covers the cost. */
    public boolean canAfford(double cost) {
        return remainingBudget >= cost;
    }

    /** Deducts a meal's cost from the remaining budget. */
    public void subtractCost(double cost) {
        remainingBudget -= cost;
    }
}
