package com.example.expensemanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanagement.Utils.Util;
import com.example.expensemanagement.enums.Constants;
import com.example.expensemanagement.Model.Expense;
import com.example.expensemanagement.enums.Month;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapters.ExpenseAdapter;
import com.example.expensemanagement.enums.SpecialCharacters;
import com.example.expensemanagement.viewModel.ExpenseViewModel;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    private Spinner spinner_select_month;
    private Spinner spinner_select_year;
    private RecyclerView recyclerView_ExpenseItems;
    private TextView tv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        spinner_select_month = findViewById(R.id.spinner_month_overview);
        spinner_select_year = findViewById(R.id.spinner_year_overview);
        tv_total = findViewById(R.id.tv_total_overview_activity);

        initRecyclerView();

        ArrayAdapter<Month> months = new ArrayAdapter<Month>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Month.values());
        spinner_select_month.setAdapter(months);
        int month = Util.getTodayDate().getMonthOfYear() - 1;
        spinner_select_month.setSelection(month);

        initYearSpinner();

        ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        List<Expense> expenseList = new ArrayList<>();

        ExpenseAdapter expenseAdapter = new ExpenseAdapter(this,expenseList,Constants.LESSDETAILS);
        recyclerView_ExpenseItems.setAdapter(expenseAdapter);
        int year = (int) spinner_select_year.getSelectedItem();
        Toast.makeText(this, "Year: "+year, Toast.LENGTH_SHORT).show();

        spinner_select_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int year = (int) spinner_select_year.getSelectedItem();
                expenseViewModel.getExpensesForMonthAndYear(i+1, year);
                expenseViewModel.getExpenseLiveData().observe(OverviewActivity.this, expenseList -> {
                    expenseAdapter.setExpenseList(expenseList);
                    tv_total.setText("Total: "+expenseAdapter.getTotal());
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        expenseAdapter.setOnItemClickListener(position -> {

            Intent intent = new Intent(OverviewActivity.this, SelectedCategoryActivity.class);
            intent.putExtra(Constants.CATEGORIES.name(),expenseViewModel.getExpenseLiveData().getValue().get(position).getCategoryName());
            intent.putExtra(Constants.MONTH.name(), String.valueOf(spinner_select_month.getSelectedItemPosition()+1));
            intent.putExtra(Constants.YEAR.name(), String.valueOf(spinner_select_year.getSelectedItem()));
            startActivity(intent);
        });
    }

    private void initYearSpinner() {
        LocalDate date = Util.getTodayDate();
        int year = date.getYear();
        int start_year = year - SpecialCharacters.DIFFERENCE_YEAR;
        List<Integer> yearList = new ArrayList<>();
        for (int i = start_year; i <= year; i++){
            yearList.add(i);
        }
        ArrayAdapter<Integer> years = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, yearList);
        spinner_select_year.setAdapter(years);
        spinner_select_year.setSelection(yearList.size()-1);
    }

    private void initRecyclerView(){

        recyclerView_ExpenseItems = findViewById(R.id.list_expenses);
        recyclerView_ExpenseItems.setLayoutManager(new LinearLayoutManager(this));
    }
}