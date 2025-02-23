package com.example.expensemanagement.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensemanagement.Model.Expense;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Utils.Util;
import com.example.expensemanagement.callbacks.SuccessFailureCallback;
import com.example.expensemanagement.csv.CSVReport;
import com.example.expensemanagement.interfaces.IExpenseReport;
import com.example.expensemanagement.repository.ExpenseRepository;
import com.example.expensemanagement.viewModel.ExpenseViewModel;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ExportActivity extends AppCompatActivity {

    private TextView tv_from_date;
    private TextView tv_to_date;

    private Button btn_export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        tv_from_date = findViewById(R.id.tv_from_date_ExportActivity);
        tv_to_date = findViewById(R.id.tv_to_date_ExportActivity);
        btn_export = findViewById(R.id.btn_export_to_csv_ExportActivity);

        tv_from_date.setOnClickListener(showDatePicker);
        tv_to_date.setOnClickListener(showDatePicker);

        btn_export.setOnClickListener(v -> {
            String from = tv_from_date.getText().toString();
            String to = tv_to_date.getText().toString();
            IExpenseReport expenseReport = new CSVReport(ExpenseRepository.getInstance());
            DateTime startDate = new DateTime(from.split(":")[1].trim());
            DateTime endDate = new DateTime(to.split(":")[1].trim());

            expenseReport.createExpenseReport(startDate, endDate, new SuccessFailureCallback<String>() {
                @Override
                public void onSuccessListener(String result) {
                    Toast.makeText(ExportActivity.this, result, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailureListener(String message) {
                    Toast.makeText(ExportActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });


        });
    }

    View.OnClickListener showDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final View vv = v;
            LocalDate todayDate = Util.getTodayDate();
            DatePickerDialog datePickerDialog = new DatePickerDialog(ExportActivity.this, (view, year, month, dayOfMonth) -> {
                LocalDate date = new LocalDate(year, month + 1, dayOfMonth);

                if (vv.getId() == R.id.tv_from_date_ExportActivity) {
                    tv_from_date.setText("From : " + date);
                } else {
                    tv_to_date.setText("To : " + date);
                }

            }, todayDate.getYear(), todayDate.getMonthOfYear(), todayDate.getDayOfMonth());
            datePickerDialog.show();
        }
    };

}