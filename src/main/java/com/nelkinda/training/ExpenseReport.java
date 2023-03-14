package com.nelkinda.training;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum ExpenseType {
    DINNER("Dinner", 5000, true),
    BREAKFAST("Breakfast", 1000, true),
    CAR_RENTAL("Car Rental", 0, false);

    ExpenseType(String label, int limit, boolean meal) {
        this.label = label;
        this.limit = limit;
        this.meal = meal;
    }

    String label;
    int limit;
    boolean meal;

    boolean isMeal() {
        return meal;
    }
}

class Expense {
    private ExpenseType type;
    private int amount;

    Expense(ExpenseType type, int amount) {
        this.type = type;
        this.amount = amount;
        validate();
    }

    ExpenseType getType() {
        return type;
    }

    int getAmount() {
        return amount;
    }

    boolean exeedsLimit() {
        return type.limit > 0 && amount > type.limit;
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

record ExpenseVM(String name, int amount, boolean mark) {
}

class ReportVM {
    private List<ExpenseVM> expenses = new ArrayList<>();
    private int total = 0;
    private int mealExpenses = 0;

    static ReportVM of(List<Expense> expenses) {
        ReportVM result = new ReportVM();
        expenses.forEach(result::add);
        return result;
    }

    void add(Expense expense) {
        expenses.add(new ExpenseVM(
                expense.getType().label,
                expense.getAmount(),
                expense.exeedsLimit()));
        total += expense.getAmount();
        if (expense.getType().isMeal()) {
            mealExpenses += expense.getAmount();
        }
    }

    public int getMealExpenses() {
        return mealExpenses;
    }

    public int getTotal() {
        return total;
    }

    public List<ExpenseVM> getExpenses() {
        return expenses;
    }

}

class ReportPrinter {

    private final String MARK = "X";
    private final String NO_MARK = " ";

    private final PrintStream output;

    ReportPrinter(PrintStream output) {
        this.output = output;
    }

    public void print(ReportVM report) {
        output.println("Expenses " + new Date());
        report.getExpenses().forEach(this::print);
        output.println("Meal expenses: " + report.getMealExpenses());
        output.println("Total expenses: " + report.getTotal());
    }

    private void print(ExpenseVM expense) {
        output.println(String.format("%s\t%d\t%s",
                expense.name(),
                expense.amount(),
                expense.mark() ? MARK : NO_MARK));
    }
}

public class ExpenseReport {

    private final ReportPrinter reportPrinter;

    ExpenseReport(PrintStream output) {
        this.reportPrinter = new ReportPrinter(output);
    }

    public void printReport(List<Expense> expenses) {
        ReportVM report = ReportVM.of(expenses);
        reportPrinter.print(report);
    }
}
