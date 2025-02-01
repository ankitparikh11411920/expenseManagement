package com.example.expensemanagement.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.expensemanagement.enums.Constants;
import com.example.expensemanagement.Model.Expense;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapters.ExpenseAdapter;
import com.example.expensemanagement.enums.SpecialCharacters;
import com.example.expensemanagement.viewModel.ExpenseViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectedCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Expense> expenseList = new ArrayList<>();
    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        Intent intent = getIntent();
        if (intent == null) return;

        recyclerView = findViewById(R.id.list_expenses_selected_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String categoryName = intent.getStringExtra(Constants.CATEGORIES.name());
        String month = intent.getStringExtra(Constants.MONTH.name());
        String year = intent.getStringExtra(Constants.YEAR.name());

        ExpenseAdapter expenseAdapter = new ExpenseAdapter(this, expenseList, Constants.ALLDETAILS);
        recyclerView.setAdapter(expenseAdapter);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getExpenseForCategory(categoryName, month, year);
        expenseViewModel.getExpenseLiveData().observe(this, list -> {
            expenseAdapter.setExpenseList(list);
            expenseList = list;
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case SpecialCharacters.DELETE:
                Expense expense = expenseList.get(item.getGroupId());
                String[] date = expense.getDate().split("-");
                expenseViewModel.removeExpense(expense.getId(), String.valueOf(Integer.parseInt(date[0])), String.valueOf(Integer.parseInt(date[1])));
                Toast.makeText(this, "Expense removed", Toast.LENGTH_SHORT).show();
                return true;

            case SpecialCharacters.UPDATE:
                // TODO update expense
                break;
        }
        return false;
    }
}