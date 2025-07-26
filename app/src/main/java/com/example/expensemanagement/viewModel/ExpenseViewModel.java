package com.example.expensemanagement.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.Model.Expense;
import com.example.expensemanagement.callbacks.SuccessFailureCallback;
import com.example.expensemanagement.repository.ExpenseRepository;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private final ExpenseRepository expenseRepository;
    private LiveData<List<Expense>> expenseLiveData;
    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = ExpenseRepository.getInstance();
    }

    public void addExpense(String categoryName, String amount, String description, LocalDate date){
        expenseRepository.addExpense(categoryName, amount,description,date);
    }
    public void getExpensesForMonthAndYear(int month, int year){
        expenseLiveData = expenseRepository.getExpensesForMonthAndYear(month, year);
    }

    public void getExpenseForCategory(String categoryName, String month, String year){
        expenseLiveData = expenseRepository.getExpensesForCategory(categoryName, month, year);
    }
    public void removeExpense(String expenseId, String year, String month){
        expenseRepository.removeExpense(expenseId,year,month);
    }

    public LiveData<List<Expense>> getExpenseLiveData(){
        return expenseLiveData;
    }

}
