package com.nelkinda.training;

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
                new Expense(ExpenseType.DINNER, 15),
                new Expense(ExpenseType.BREAKFAST, 5),
                new Expense(ExpenseType.CAR_RENTAL, 115));

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
    void printReport_mealOverExpenses_markedWithAnX() {
        // arrange
        List<Expense> expenses = List.of(
                new Expense(ExpenseType.DINNER, 5001),
                new Expense(ExpenseType.BREAKFAST, 1001),
                new Expense(ExpenseType.CAR_RENTAL, 115));

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

}