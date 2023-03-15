package com.nelkinda.training;

import lombok.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@RequiredArgsConstructor
enum ExpenseType {
    BREAKFAST("Breakfast", 1000, true),
    LUNCH("Lunch", 2000, true),
    DINNER("Dinner", 5000, true),
    CAR_RENTAL("Car Rental", 0, false);

    final String label;
    final int limit;
    final boolean meal;
}

@Value
class Expense {
    ExpenseType type;
    int amount;

    boolean exeedsLimit() {
        return type.limit > 0 && amount > type.limit;
    }
}

record ExpenseVM(String name, int amount, boolean mark) {
}

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ReportVM {
    private List<ExpenseVM> expenses = new ArrayList<>();
    private int total = 0;
    private int mealExpenses = 0;

    static ReportVM of(List<Expense> expenses) {
        val result = new ReportVM();
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
}

@RequiredArgsConstructor
class ReportPrinter {

    private static final String MARK = "X";
    private static final String NO_MARK = " ";

    private final PrintStream output;

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
        val report = ReportVM.of(expenses);
        reportPrinter.print(report);
    }
}
