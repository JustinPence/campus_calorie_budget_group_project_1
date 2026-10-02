/**
 * Tracks daily calorie consumption against a gender-based goal.
 */
public class CalorieTracker {

    // Daily calorie goals and the approximate calories burned per mile jogged
    private static final int FEMALE_GOAL = 2000;
    private static final int MALE_GOAL = 2500;
    private static final int CALORIES_PER_MILE = 100;

    private int goal;           // this user's daily calorie goal
    private int totalCalories;  // calories eaten so far today

    /** Sets the daily goal: 2000 for "female", 2500 for "male". */
    public void setGoal(String gender) {
        // Any value other than "female" uses the male goal
        goal = gender.equalsIgnoreCase("female") ? FEMALE_GOAL : MALE_GOAL;
    }

    public int getGoal() {
        return goal;
    }

    /** Adds a meal's calories to the running daily total. */
    public void addCalories(int amount) {
        totalCalories += amount;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public boolean isUnderGoal() {
        return totalCalories < goal;
    }

    public boolean isAtGoal() {
        return totalCalories == goal;
    }

    public boolean isOverGoal() {
        return totalCalories > goal;
    }

    /** Returns how many calories over the goal, or 0 if not over. */
    public int getExcessCalories() {
        return isOverGoal() ? totalCalories - goal : 0;
    }

    /** Returns the miles of jogging needed to burn the excess calories. */
    public double calculateJoggingMiles() {
        return (double) getExcessCalories() / CALORIES_PER_MILE;
    }
}
