import javax.swing.JOptionPane;

/**
 * Handles all user input and output using JOptionPane dialog boxes.
 * No other class talks to the user directly; MealPlanner calls these
 * methods so the program logic stays separate from the presentation.
 */
public class UserInterface {

    // Title shown at the top of every dialog box
    private static final String TITLE = "GMU Campus Meal Planner";

    private final MealData mealData = new MealData();

    /** Shows the welcome message at program start. */
    public void displayWelcome() {
        showMessage("Welcome to the GMU Campus Budget & Calorie Meal Planner!");
    }

    /** Asks for the daily budget, re-prompting until a positive number is entered. */
    public double promptForBudget() {
        while (true) {
            String input = prompt("What is your daily meal budget? ($)");
            try {
                double budget = Double.parseDouble(input);
                // Reject zero or negative budgets
                if (budget > 0) {
                    return budget;
                }
                showError("Your budget must be greater than $0. Please try again.");
            } catch (NumberFormatException e) {
                showError("Please enter a valid number (for example, 25 or 30.50).");
            }
        }
    }

    /** Asks for gender (M or F) and returns "male" or "female". */
    public String promptForGender() {
        while (true) {
            String input = prompt("What is your gender? (M/F)");
            if (input.equalsIgnoreCase("M")) {
                return "male";
            }
            if (input.equalsIgnoreCase("F")) {
                return "female";
            }
            showError("Please enter M or F.");
        }
    }

    /** Shows the user's daily calorie goal. */
    public void displayCalorieGoal(int goal) {
        showMessage(String.format("Your daily calorie goal is %d calories.", goal));
    }

    /** Tells the user their starting budget can't cover even the cheapest meal. */
    public void displayBudgetTooLow(double budget, double cheapestMeal) {
        showMessage(String.format("Your budget of $%.2f isn't enough for any meal (cheapest is $%.2f).",
                budget, cheapestMeal));
    }

    /** Asks which meal to eat and returns "breakfast", "lunch", or "dinner". */
    public String promptForMealType() {
        while (true) {
            String input = prompt("Which meal? Breakfast (B), Lunch (L), Dinner (D)?");
            if (input.equalsIgnoreCase("B")) {
                return "breakfast";
            }
            if (input.equalsIgnoreCase("L")) {
                return "lunch";
            }
            if (input.equalsIgnoreCase("D")) {
                return "dinner";
            }
            showError("Please enter B, L, or D.");
        }
    }

    /** Builds the numbered list of meal options (name, cost, calories) for a meal type. */
    private String buildMealOptions(String mealType) {
        String[] names = mealData.getMealOptions(mealType);
        StringBuilder menu = new StringBuilder("Here are today's " + mealType + " options:\n\n");
        for (int i = 0; i < names.length; i++) {
            // Options are shown 1-based; the arrays are 0-based
            menu.append(String.format("%d. %s  -  $%.2f  (%d cal)%n", i + 1, names[i],
                    mealData.getMealCost(mealType, i), mealData.getMealCalories(mealType, i)));
        }
        return menu.toString();
    }

    /** Shows the meal options and returns the user's choice (1, 2, or 3). */
    public int getMealChoice(String mealType) {
        String menu = buildMealOptions(mealType);
        while (true) {
            String input = prompt(menu + "\nEnter your choice (1-3):");
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 3) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // fall through to the error message
            }
            showError("Please enter 1, 2, or 3.");
        }
    }

    /** Shows what was just eaten along with the current budget and calorie totals. */
    public void displayMealConfirmation(String mealName, double cost, int calories,
                                        double remainingBudget, int totalCalories) {
        showMessage(String.format("Enjoy your %s! That cost $%.2f and has %d calories.%n%n"
                        + "Remaining budget: $%.2f%nTotal calories so far: %d",
                mealName, cost, calories, remainingBudget, totalCalories));
    }

    /** Asks whether the user wants another meal using Yes/No buttons. */
    public boolean askForAnotherMeal() {
        int answer = JOptionPane.showConfirmDialog(null, "Want another meal?", TITLE,
                JOptionPane.YES_NO_OPTION);
        // Closing the dialog counts as "No"
        return answer == JOptionPane.YES_OPTION;
    }

    /** Tells the user they can't afford the selected meal. */
    public void displayInsufficientBudget(String mealName, double cost) {
        showMessage(String.format("Sorry, the %s costs $%.2f and you don't have enough left in your budget.%n"
                + "Let's pick something else.", mealName, cost));
    }

    /** Tells the user the remaining budget can't cover another meal. */
    public void displayOutOfBudget() {
        showMessage("You don't have enough budget left for another meal today.");
    }

    /** Tells the user they have reached their calorie goal. */
    public void displayGoalReached() {
        showMessage("You've reached your calorie goal for the day.");
    }

    /**
     * Shows the final calorie total compared to the goal, followed by either a
     * success message or a jogging suggestion when the goal was exceeded.
     */
    public void displayFinalSummary(int totalCalories, int goal, double joggingMiles) {
        StringBuilder summary = new StringBuilder("===== Daily Summary =====\n\n");
        summary.append(String.format("Total calories consumed: %d%n", totalCalories));
        summary.append(String.format("Daily calorie goal: %d%n%n", goal));

        // Compare the total to the goal and pick the closing message
        if (totalCalories < goal) {
            summary.append(String.format("You are %d calories under your goal.%n", goal - totalCalories));
        } else if (totalCalories == goal) {
            summary.append("You hit your goal exactly!\n");
        } else {
            summary.append(String.format("You are %d calories over your goal.%n", totalCalories - goal));
        }

        if (totalCalories > goal) {
            summary.append(String.format("To burn off the extra calories, try jogging about %.1f miles around campus.",
                    joggingMiles));
        } else {
            summary.append("Great job staying within your calorie goal today! Go Patriots!");
        }
        showMessage(summary.toString());
    }

    /**
     * Shows an input dialog and returns the trimmed text. If the user presses
     * Cancel or closes the dialog, the program exits cleanly instead of crashing.
     */
    private String prompt(String message) {
        String input = JOptionPane.showInputDialog(null, message, TITLE, JOptionPane.QUESTION_MESSAGE);
        if (input == null) {
            showMessage("Goodbye!");
            System.exit(0);
        }
        return input.trim();
    }

    /** Shows an informational message dialog. */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    /** Shows an error message dialog for invalid input. */
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.ERROR_MESSAGE);
    }
}
