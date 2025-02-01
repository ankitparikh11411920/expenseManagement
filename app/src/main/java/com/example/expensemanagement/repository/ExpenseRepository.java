package com.example.expensemanagement.repository;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanagement.Model.Expense;
import com.example.expensemanagement.Utils.Util;
import com.example.expensemanagement.enums.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ExpenseRepository {


    private static final String TAG = "ExpenseRepository";
    public static final String CATEGORY_NAME = "categoryName";
    private DatabaseReference myRef;
    private FirebaseUser user;
    private static ExpenseRepository instance;
    private static String currentUserId;

    private ExpenseRepository() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        getUserId();
    }

    public static ExpenseRepository getInstance() {

        if (instance == null) {
            instance = new ExpenseRepository();
        }
        return instance;
    }

    /**
     * Gets all the expenses for given month<br>
     */
    public MutableLiveData<List<Expense>> getExpensesForMonthAndYear(int month, int year) {
        MutableLiveData<List<Expense>> expenses = new MutableLiveData<>();
        myRef.child(String.valueOf(year)).child(String.valueOf(month)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String, Expense> map = new HashMap<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expense expense = dataSnapshot.getValue(Expense.class);
                    if (expense == null) {
                        break;
                    }
                    if (map.containsKey(expense.getCategoryName())) {
                        Expense expense1 = map.get(expense.getCategoryName());
                        float total = Float.parseFloat(expense1.getAmount()) + Float.parseFloat(expense.getAmount());
                        expense1.setAmount(String.valueOf(total));
                        map.put(expense.getCategoryName(), expense1);
                    } else {
                        map.put(expense.getCategoryName(), expense);
                    }
                }
                List<Expense> expenseList = new ArrayList<>(map.values());
                expenses.setValue(expenseList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        });
        return expenses;
    }

    public void addExpense(String categoryName, String amount, String description, LocalDate date) {
        String id = UUID.randomUUID().toString();
        categoryName = Util.makeFirstCharacterCapital(categoryName);
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonthOfYear());

        Expense expense = new Expense(categoryName, amount, date.toString(), id, description);
        myRef.child(year).child(month).child(id).setValue(expense);
    }

    public void removeExpense(String expenseId, String year, String month) {
        myRef.child(year).child(month).child(expenseId).removeValue();
    }

    public MutableLiveData<List<Expense>> getExpensesForCategory(String categoryName, String month, String year) {

        MutableLiveData<List<Expense>> expensesForCategory = new MutableLiveData<>();
        Query query = myRef.child(year).child(month).orderByChild(CATEGORY_NAME).equalTo(categoryName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Expense> expenseList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Expense e = dataSnapshot.getValue(Expense.class);
                        expenseList.add(e);
                    }
                    Collections.sort(expenseList, (expense, t1) -> {
                        return expense.getDate().compareTo(t1.getDate());
                    });
                    expensesForCategory.postValue(expenseList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return expensesForCategory;
    }

    public void addUserId(String userId) {
        if (userId == null || userId.isEmpty()) return;
        currentUserId = userId;
        myRef.child(Constants.USERID.name().toLowerCase()).setValue(userId);
    }

    public String getCurrentUserId() {
        if (currentUserId == null) getUserId();
        return currentUserId;
    }

    private void getUserId() {
        myRef.child(Constants.USERID.name().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUserId = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void destroyInstance() {
        if (instance != null) {
            instance = null;
        }
    }
}
