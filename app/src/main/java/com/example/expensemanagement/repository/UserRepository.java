package com.example.expensemanagement.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanagement.Model.User;
import com.example.expensemanagement.enums.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class UserRepository {

    private static final String TAG = "UserRepository";
    public static final String EMAIL = "email";
    private DatabaseReference myRef;
    private static UserRepository instance;
    private final ExpenseRepository expenseRepository;

    private UserRepository() {
        myRef = FirebaseDatabase.getInstance().getReference(Constants.USERS.name().toLowerCase());
        expenseRepository = ExpenseRepository.getInstance();
    }

    public static UserRepository getInstance() {

        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void addUser(String name, String email) {

        if (name == null || email == null || name.isEmpty() || email.isEmpty()) {
            return;
        }
        String id = UUID.randomUUID().toString();
        User user = new User(id, name, email);

        Query query = myRef.orderByChild(EMAIL).equalTo(email).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    myRef.child(id).setValue(user);
                    expenseRepository.addUserId(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
