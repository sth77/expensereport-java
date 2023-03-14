package com.nelkinda.training;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER, BREAKFAST, CAR_RENTAL
}

class Expense {
    private ExpenseType type;
    private int amount;

    Expense(ExpenseType type, int amount) {
        this.type = type;
        this.amount = amount;
        validate();
    }

    public ExpenseType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    private void validate() {
        if (type == null) {
            throw new IllegalArgumentException("type is null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }
}

public class ExpenseReport {

    private final PrintStream output;

    private ExpenseReport(PrintStream output) {
        this.output = output;
    }

    public void printReport(List<Expense> expenses) {
        int total = 0;
        int mealExpenses = 0;

        output.println("Expenses " + new Date());

        for (Expense expense : expenses) {
            if (expense.getType() == ExpenseType.DINNER || expense.getType() == ExpenseType.BREAKFAST) {
                mealExpenses += expense.getAmount();
            }

            String expenseName = "";
            switch (expense.getType()) {
            case DINNER:
                expenseName = "Dinner";
                break;
            case BREAKFAST:
                expenseName = "Breakfast";
                break;
            case CAR_RENTAL:
                expenseName = "Car Rental";
                break;
            }

            String mealOverExpensesMarker = expense.getType() == ExpenseType.DINNER && expense.getAmount() > 5000 || expense.getType() == ExpenseType.BREAKFAST && expense.getAmount() > 1000 ? "X" : " ";

            output.println(expenseName + "\t" + expense.getAmount() + "\t" + mealOverExpensesMarker);

            total += expense.getAmount();
        }

        output.println("Meal expenses: " + mealExpenses);
        output.println("Total expenses: " + total);
    }
}
