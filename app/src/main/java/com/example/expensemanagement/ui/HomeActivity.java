package com.example.expensemanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.expensemanagement.R;
import com.example.expensemanagement.Utils.Util;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button btn_overview;
    private Button btn_add_expense;
    private Button btn_add_category;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_add_expense = findViewById(R.id.btn_add_expense_home);
        btn_overview = findViewById(R.id.btn_overview_home);
        btn_add_category = findViewById(R.id.btn_add_category_home);

        btn_logout = findViewById(R.id.btn_logout);

        btn_overview.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), OverviewActivity.class)));

        btn_add_expense.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddExpenseActivity.class)));

        btn_add_category.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddCategoryActivity.class)));

        btn_logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }
}