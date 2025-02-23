package com.example.expensemanagement.csv;

import android.os.Environment;

import com.example.expensemanagement.Model.Expense;
import com.example.expensemanagement.callbacks.SuccessFailureCallback;
import com.example.expensemanagement.enums.ExcelHeaders;
import com.example.expensemanagement.interfaces.IExpenseReport;
import com.example.expensemanagement.repository.ExpenseRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class CSVReport implements IExpenseReport {

    private ExpenseRepository expenseRepository;
    public CSVReport(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }
    @Override
    public void createExpenseReport(DateTime startDate, DateTime endDate, SuccessFailureCallback<String> successFailureCallback) {

        if(startDate.isAfter(endDate)) {
            successFailureCallback.onFailureListener("FROM date cannot be after TO date");
            return;
        }
        expenseRepository.getExpensesForInterval(startDate, endDate, new SuccessFailureCallback<>() {
            @Override
            public void onSuccessListener(List<Expense> result) {

                Comparator<Expense> comparator = (o1, o2) -> (new DateTime(o1.getDate()).compareTo(new DateTime(o2.getDate())));
                Collections.sort(result, comparator);

                String fileName = getFileName(startDate, endDate);
                String downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                try (FileWriter writer = new FileWriter(new File(downloadDirectory, fileName));
                     CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create().setHeader(ExcelHeaders.class).build())) {

                    for (Expense e : result) {
                        csvPrinter.printRecord(e.getCategoryName(), e.getDate(), e.getAmount());
                    }
                    csvPrinter.flush();
                    successFailureCallback.onSuccessListener("File downloaded at : " + downloadDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                    successFailureCallback.onFailureListener("Something went wrong!");
                }
            }

            @Override
            public void onFailureListener(String message) {

            }
        });


    }
    private String getFileName(DateTime startDate, DateTime endDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("YYYYMM");
        return startDate.toString(dateTimeFormatter)+"_"+endDate.toString(dateTimeFormatter)+".csv";
    }
}
