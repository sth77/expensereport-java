package com.nelkinda.training;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExpenseReportTest {

    @InjectMocks
    private ExpenseReport expenseReport;

    @Mock
    private PrintStream output;

    @Test
    void printReport() {
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
    }
}