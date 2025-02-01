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
    private ExpenseRepository expenseRepository;
    private static String currentUserId;

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

        currentUserId = id;
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

    public MutableLiveData<User> findUser(String email) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        if (email == null || email.isEmpty()) {
            return null;
        }

        Query query = myRef.orderByChild(EMAIL).equalTo(email).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        userMutableLiveData.setValue(user);
                        break;
                    }
                } else {
                    userMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return userMutableLiveData;
    }


    public Boolean addFriend(User user) {
        if (user == null || user.getId() == null || user.getName() == null) return false;
        String currentUserId = expenseRepository.getCurrentUserId();
        myRef.child(currentUserId).child(Constants.FRIENDS.name().toLowerCase()).child(user.getId()).setValue(user.getName());
        return true;
    }

    public String getCurrentUserId(){
        return currentUserId;
    }
    public static void destroyInstance() {
        if(instance != null){
            instance = null;
        }
    }
}
