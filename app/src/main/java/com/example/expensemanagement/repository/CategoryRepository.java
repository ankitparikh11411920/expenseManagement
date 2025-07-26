package com.example.expensemanagement.repository;


import static com.example.expensemanagement.enums.Constants.CATEGORIES;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanagement.Utils.Util;
import com.example.expensemanagement.callbacks.SuccessFailureCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CategoryRepository {
    private static final String TAG = "CategoryRepository";
    private DatabaseReference myRef;
    private FirebaseUser user;
    private static CategoryRepository instance;

    private CategoryRepository() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(CATEGORIES.name().toLowerCase());
    }

    public static CategoryRepository getInstance() {

        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }


    public void addCategory(String categoryName) {
        categoryName = Util.makeFirstCharacterCapital(categoryName);
        myRef.child(categoryName).setValue(categoryName);
    }

    public MutableLiveData<List<String>> getAllCategories() {

        MutableLiveData<List<String>> categoryItems = new MutableLiveData<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> categoryList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String name = dataSnapshot.getKey();
                        categoryList.add(name);
                    }
                    categoryItems.postValue(categoryList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return categoryItems;
    }

    public static void destroyInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public void removeCategory(String categoryName, SuccessFailureCallback<String> successFailureCallback) {
        myRef.child(categoryName)
            .removeValue()
            .addOnSuccessListener(unused -> successFailureCallback.onSuccessListener(categoryName + " removed."))
            .addOnFailureListener(e -> successFailureCallback.onFailureListener(e.getMessage()));
    }
}
