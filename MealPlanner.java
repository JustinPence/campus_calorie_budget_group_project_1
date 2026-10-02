/**
 * Main program: guides a GMU student through choosing meals while tracking
 * their budget and calorie intake. All user interaction goes through
 * UserInterface (JOptionPane dialogs); this class only coordinates the flow.
 */
public class MealPlanner {

    public static void main(String[] args) {
        // Create the components the program is built from
        MealData mealData = new MealData();
        BudgetTracker budget = new BudgetTracker();
        CalorieTracker calories = new CalorieTracker();
        UserInterface ui = new UserInterface();

        ui.displayWelcome();

        // Collect the user's budget and gender, then set the calorie goal
        budget.setBudget(ui.promptForBudget());
        String gender = ui.promptForGender();
        calories.setGoal(gender);
        ui.displayCalorieGoal(calories.getGoal());

        // If the starting budget can't buy even the cheapest meal, skip the meal loop
        double cheapestMeal = mealData.getCheapestMealCost();
        if (!budget.canAfford(cheapestMeal)) {
            ui.displayBudgetTooLow(budget.getRemainingBudget(), cheapestMeal);
        }

        // Main meal loop: runs until the user stops, runs out of money, or reaches the goal
        boolean keepEating = budget.canAfford(cheapestMeal);
        while (keepEating) {
            String mealType = ui.promptForMealType();
            int index = ui.getMealChoice(mealType) - 1;  // convert 1-3 choice to array index

            // Look up the chosen meal's details
            String mealName = mealData.getMealOptions(mealType)[index];
            double cost = mealData.getMealCost(mealType, index);
            int mealCalories = mealData.getMealCalories(mealType, index);

            // Too expensive: tell the user and let them choose again
            if (!budget.canAfford(cost)) {
                ui.displayInsufficientBudget(mealName, cost);
                continue;
            }

            // Record the meal against the budget and the calorie total
            budget.subtractCost(cost);
            calories.addCalories(mealCalories);
            ui.displayMealConfirmation(mealName, cost, mealCalories,
                    budget.getRemainingBudget(), calories.getTotalCalories());

            // Decide whether the loop should continue
            if (!budget.canAfford(cheapestMeal)) {
                ui.displayOutOfBudget();
                keepEating = false;
            } else if (calories.isAtGoal() || calories.isOverGoal()) {
                ui.displayGoalReached();
                keepEating = false;
            } else {
                keepEating = ui.askForAnotherMeal();
            }
        }

        // End-of-day summary with a success message or jogging suggestion
        ui.displayFinalSummary(calories.getTotalCalories(), calories.getGoal(),
                calories.calculateJoggingMiles());
    }
}
