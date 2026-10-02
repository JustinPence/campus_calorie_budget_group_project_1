/**
 * Stores all meal information as constants using parallel arrays.
 * Index i in the names, costs, and calories arrays describes the same meal.
 */
public class MealData {

    // Breakfast menu
    private static final String[] BREAKFAST_NAMES = {"Oatmeal with fruit", "Eggs and toast", "Breakfast burrito"};
    private static final double[] BREAKFAST_COSTS = {5.0, 6.0, 7.0};
    private static final int[] BREAKFAST_CALORIES = {350, 400, 550};

    // Lunch menu
    private static final String[] LUNCH_NAMES = {"Turkey sandwich", "Pasta with marinara", "Grilled chicken salad"};
    private static final double[] LUNCH_COSTS = {8.0, 9.0, 8.0};
    private static final int[] LUNCH_CALORIES = {600, 750, 500};

    // Dinner menu
    private static final String[] DINNER_NAMES = {"Beef and rice bowl", "Pizza (2 slices)", "Salmon with vegetables"};
    private static final double[] DINNER_COSTS = {11.0, 10.0, 12.0};
    private static final int[] DINNER_CALORIES = {850, 700, 650};

    /** Returns the meal names for "breakfast", "lunch", or "dinner". */
    public String[] getMealOptions(String mealType) {
        switch (mealType.toLowerCase()) {
            case "breakfast": return BREAKFAST_NAMES;
            case "lunch":     return LUNCH_NAMES;
            case "dinner":    return DINNER_NAMES;
            default: throw new IllegalArgumentException("Unknown meal type: " + mealType);
        }
    }

    /** Returns the cost in dollars of the meal at the given index. */
    public double getMealCost(String mealType, int index) {
        switch (mealType.toLowerCase()) {
            case "breakfast": return BREAKFAST_COSTS[index];
            case "lunch":     return LUNCH_COSTS[index];
            case "dinner":    return DINNER_COSTS[index];
            default: throw new IllegalArgumentException("Unknown meal type: " + mealType);
        }
    }

    /** Returns the calorie count of the meal at the given index. */
    public int getMealCalories(String mealType, int index) {
        switch (mealType.toLowerCase()) {
            case "breakfast": return BREAKFAST_CALORIES[index];
            case "lunch":     return LUNCH_CALORIES[index];
            case "dinner":    return DINNER_CALORIES[index];
            default: throw new IllegalArgumentException("Unknown meal type: " + mealType);
        }
    }

    /** Returns the price of the cheapest meal on the menu. */
    public double getCheapestMealCost() {
        // Scan every cost array and keep the smallest value
        double cheapest = Double.MAX_VALUE;
        for (double[] costs : new double[][] {BREAKFAST_COSTS, LUNCH_COSTS, DINNER_COSTS}) {
            for (double cost : costs) {
                cheapest = Math.min(cheapest, cost);
            }
        }
        return cheapest;
    }
}
