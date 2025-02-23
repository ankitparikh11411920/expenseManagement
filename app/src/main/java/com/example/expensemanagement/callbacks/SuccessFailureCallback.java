package com.example.expensemanagement.callbacks;

public interface SuccessFailureCallback<T> {

    void onSuccessListener(T result);
    void onFailureListener(String message);
}
