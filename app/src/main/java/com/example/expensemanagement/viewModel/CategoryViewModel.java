package com.example.expensemanagement.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<String>> categoryList;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = CategoryRepository.getInstance();
    }

    public void addCategory(String categoryName){
        categoryRepository.addCategory(categoryName);
    }

    public List<String> getAllCategories(){
        categoryList = categoryRepository.getAllCategories();
        return categoryList.getValue();
    }

    public LiveData<List<String>> getCategoryList() {
        return categoryList;
    }
}

