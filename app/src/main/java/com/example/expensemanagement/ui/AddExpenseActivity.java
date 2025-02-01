package com.example.expensemanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanagement.R;
import com.example.expensemanagement.Utils.Util;
import com.example.expensemanagement.Validators.FieldValidator;
import com.example.expensemanagement.Validators.ValidationResult;
import com.example.expensemanagement.Validators.ValidationsUtils;
import com.example.expensemanagement.Validators.Validator;
import com.example.expensemanagement.ui.fragment.DatePickerFragment;
import com.example.expensemanagement.viewModel.CategoryViewModel;
import com.example.expensemanagement.viewModel.ExpenseViewModel;

import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Spinner spinner_categoryName;
    private EditText edt_amount;
    private EditText edt_description;
    private TextView tv_date;
    private LocalDate finalDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        spinner_categoryName = findViewById(R.id.spinner_categoryName_addExpense);
        edt_amount = findViewById(R.id.edt_amount_addExpense);
        edt_description = findViewById(R.id.edt_description_addExpense);
        tv_date = findViewById(R.id.tv_date_AddExpenseActivity);
        Button btn_addExpense = findViewById(R.id.btn_addExpense_addExpense);

        finalDate = Util.getTodayDate();
        tv_date.setText(finalDate.toString());
        ExpenseViewModel expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        categoryViewModel.getAllCategories();
        List<String> categories = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                categories);

        categoryViewModel.getCategoryList().observe(this, strings -> {
            categories.addAll(strings);
            arrayAdapter.notifyDataSetChanged();
        });
        spinner_categoryName.setAdapter(arrayAdapter);

        btn_addExpense.setOnClickListener(view -> {
            String amount;
            String categoryName;
            String description;

            amount = edt_amount.getText().toString();
            categoryName = spinner_categoryName.getSelectedItem().toString();
            description = edt_description.getText().toString();
            List<Validator> validatorList = new ArrayList<>();
            validatorList.add(new FieldValidator("Category", categoryName));
            validatorList.add(new FieldValidator("Amount", amount));
//            validatorList.add(new FieldValidator("Description",description));
            ValidationResult validationResult = ValidationsUtils.validate(validatorList);

            if(!validationResult.isValid()){
                Toast.makeText(AddExpenseActivity.this, validationResult.getResponse(), Toast.LENGTH_SHORT).show();
                return;
            }

            expenseViewModel.addExpense(categoryName, amount,description,finalDate);
            Toast.makeText(AddExpenseActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
            finish();
        });

        tv_date.setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(),"Date Picker");
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        LocalDate date = new LocalDate(year,month+1,day);
        finalDate = date;
        tv_date.setText(finalDate.toString());
    }
}