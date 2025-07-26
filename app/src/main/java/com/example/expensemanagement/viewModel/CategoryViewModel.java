package com.example.expensemanagement.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.callbacks.SuccessFailureCallback;
import com.example.expensemanagement.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryRepository categoryRepository;
    private LiveData<List<String>> categoryList;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = CategoryRepository.getInstance();
    }

    public void addCategory(String categoryName){
        categoryRepository.addCategory(categoryName);
    }

    public void getAllCategories(){
        categoryList = categoryRepository.getAllCategories();
    }

    public LiveData<List<String>> getCategoryList() {
        return categoryList;
    }

    public void removeCategory(String categoryName, SuccessFailureCallback<String> successFailureCallback) {
        if(categoryName == null || categoryName.isEmpty()){
            return;
        }
        categoryRepository.removeCategory(categoryName, successFailureCallback);
    }
}

