package com.example.expensemanagement.interfaces;

import com.example.expensemanagement.callbacks.SuccessFailureCallback;

import org.joda.time.DateTime;

public interface IExpenseReport {

    void createExpenseReport(DateTime startDate, DateTime endDate, SuccessFailureCallback<String> successFailureCallback);

}
