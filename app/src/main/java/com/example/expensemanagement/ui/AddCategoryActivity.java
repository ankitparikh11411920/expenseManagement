package com.example.expensemanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensemanagement.R;
import com.example.expensemanagement.Validators.FieldValidator;
import com.example.expensemanagement.Validators.ValidationResult;
import com.example.expensemanagement.Validators.ValidationsUtils;
import com.example.expensemanagement.viewModel.CategoryViewModel;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText edt_categoryName;
    private Button btn_addCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        edt_categoryName = findViewById(R.id.edt_categoryName_addCategory);
        btn_addCategory = findViewById(R.id.btn_addCategory_addCategory);

        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        btn_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryName = edt_categoryName.getText().toString();

                ValidationResult validationResult = ValidationsUtils.validate(new FieldValidator("Category Name", categoryName));

                if(!validationResult.isValid()){
                    Toast.makeText(AddCategoryActivity.this, validationResult.getResponse(), Toast.LENGTH_SHORT).show();
                    return;
                }
                
                categoryViewModel.addCategory(categoryName);
                Toast.makeText(AddCategoryActivity.this, "Successfully Added Category", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}