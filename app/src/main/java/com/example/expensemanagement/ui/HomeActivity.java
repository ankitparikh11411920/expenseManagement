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
    private Button btn_find_user;
    private Button btn_logout;
    private Button btn_show_requests;
    private Button btn_show_connections;
    private Button btn_upload_to_drive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_add_expense = findViewById(R.id.btn_add_expense_home);
        btn_overview = findViewById(R.id.btn_overview_home);
        btn_add_category = findViewById(R.id.btn_add_category_home);
        btn_find_user = findViewById(R.id.btn_find_user_home);
        btn_show_requests = findViewById(R.id.btn_show_requests);
        btn_show_connections = findViewById(R.id.btn_show_connections);
        btn_upload_to_drive = findViewById(R.id.btn_upload_to_drive);

        btn_logout = findViewById(R.id.btn_logout);

        btn_overview.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), OverviewActivity.class)));

        btn_add_expense.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddExpenseActivity.class)));

        btn_add_category.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddCategoryActivity.class)));

        btn_find_user.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), FindUsersActivity.class)));

        btn_show_requests.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PendingRequestActivity.class)));

        btn_show_connections.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ConnectionsActivity.class)));
        btn_upload_to_drive.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UploadActivity.class)));

        btn_logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Util.destroyAllInstances();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }
}