package com.example.expensemanagement.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapters.CategoryAdapter;
import com.example.expensemanagement.callbacks.SuccessFailureCallback;
import com.example.expensemanagement.enums.SpecialCharacters;
import com.example.expensemanagement.viewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    private CategoryViewModel categoryViewModel;
    int selectedItemPosition = -1;
    private List<String> categoryList = new ArrayList<>();

    private final String TAG = "CategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryAdapter = new CategoryAdapter(this, categoryList, position -> {
            selectedItemPosition = position;
        });
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories();
        categoryViewModel.getCategoryList().observe(this, strings -> {
            categoryList.clear();
            categoryList.addAll(strings);
            categoryAdapter.setCategoryList(strings);
            categoryAdapter.notifyDataSetChanged();
        });

        initRecyclerView();

        // Swipe to delete
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int pos = viewHolder.getAdapterPosition();
                        categoryList.remove(pos);
                        removeCategory(pos);
                    }
                };

        new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(recyclerView);
    }

    private void removeCategory(int pos) {
        categoryViewModel.removeCategory(categoryList.get(pos), new SuccessFailureCallback<String>() {
            @Override
            public void onSuccessListener(String result) {
                categoryAdapter.notifyItemRemoved(pos);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailureListener(String message) {
                Log.d(TAG, "Error occurred while removing category.Message=" + message);
                Toast.makeText(CategoryActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);
        if (item.getItemId() == SpecialCharacters.DELETE) {
            if(selectedItemPosition != -1){
                removeCategory(selectedItemPosition);
            }
            return true;
        }

        return false;
    }
}
