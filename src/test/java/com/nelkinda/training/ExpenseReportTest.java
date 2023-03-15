package com.nelkinda.training;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ExpenseReportTest {

    @InjectMocks
    private ExpenseReport expenseReport;

    @Mock
    private PrintStream output;

    @Test
    void printReport_regularExpensesGiven_printed() {
        // arrange
        List<Expense> expenses = List.of(
                dinner(15),
                breakfast(5),
                carRental(115));

        // act
        expenseReport.printReport(expenses);

        // assert
        verify(output).println(startsWith("Expenses "));
        verify(output).println(eq("Dinner\t15\t "));
        verify(output).println(eq("Breakfast\t5\t "));
        verify(output).println(eq("Car Rental\t115\t "));
        verify(output).println(eq("Meal expenses: 20"));
        verify(output).println(eq("Total expenses: 135"));
        verifyNoMoreInteractions(output);
    }

    @Test
    void printReport_emtpyListGiven_onlyFirstLinePrinted() {
        // arrange
        List<Expense> expenses = List.of();

        // act
        expenseReport.printReport(expenses);

        // assert
        verify(output).println(startsWith("Expenses "));
        verify(output).println(eq("Meal expenses: 0"));
        verify(output).println(eq("Total expenses: 0"));
        verifyNoMoreInteractions(output);
    }

    @Test
    void printReport_mealOverLimit_markedWithAnX() {
        // arrange
        List<Expense> expenses = List.of(
                dinner(5001),
                breakfast(1001),
                carRental(115));

        // act
        expenseReport.printReport(expenses);

        // assert
        verify(output).println(startsWith("Expenses "));
        verify(output).println(eq("Dinner\t5001\tX"));
        verify(output).println(eq("Breakfast\t1001\tX"));
        verify(output).println(eq("Car Rental\t115\t "));
        verify(output).println(eq("Meal expenses: 6002"));
        verify(output).println(eq("Total expenses: 6117"));
        verifyNoMoreInteractions(output);
    }

    @Test
    void printReport_lunchOverLimit_markedWithAnX() {
        // arrange
        val expenses = List.of(lunch(2000));

        // act
        expenseReport.printReport(expenses);

        // assert
        verify(output).println(startsWith("Expenses "));
        verify(output).println(eq("Lunch\t2001\tX"));
        verify(output).println(eq("Meal expenses: 2001"));
        verify(output).println(eq("Total expenses: 2001"));
        verifyNoMoreInteractions(output);
    }

    @Test
    void printReport_lunchBelowLimit_printedWithoutAnX() {
        // arrange
        List<Expense> expenses = List.of(lunch(2000));

        // act
        expenseReport.printReport(expenses);

        // assert
        verify(output).println(startsWith("Expenses "));
        verify(output).println(eq("Lunch\t2000\t "));
        verify(output).println(eq("Meal expenses: 2000"));
        verify(output).println(eq("Total expenses: 2000"));
        verifyNoMoreInteractions(output);
    }

    private Expense breakfast(int amount) {
        return new Expense(ExpenseType.BREAKFAST, amount);
    }

    private Expense lunch(int amount) {
        return new Expense(ExpenseType.LUNCH, amount);
    }

    private Expense dinner(int amount) {
        return new Expense(ExpenseType.DINNER, amount);
    }

    private Expense carRental(int amount) {
        return new Expense(ExpenseType.CAR_RENTAL, amount);
    }

}