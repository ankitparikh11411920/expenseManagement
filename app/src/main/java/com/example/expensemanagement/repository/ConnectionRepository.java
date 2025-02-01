package com.example.expensemanagement.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanagement.Model.ConnectionRequest;
import com.example.expensemanagement.Model.User;
import com.example.expensemanagement.enums.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectionRepository {

    private static final String TAG = "ConnectionRepository";
    private DatabaseReference myRef;
    private FirebaseUser user;
    private static ConnectionRepository instance;
    private ExpenseRepository expenseRepository;

    private ConnectionRepository() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child(Constants.CONNECTIONS.name().toLowerCase());
        expenseRepository = ExpenseRepository.getInstance();
    }

    public static ConnectionRepository getInstance() {

        if (instance == null) {
            instance = new ConnectionRepository();
        }
        return instance;
    }

    public MutableLiveData<Boolean> sendConnectionRequest(User user){
        MutableLiveData<Boolean> status = new MutableLiveData<>();
        String currentUserId = expenseRepository.getCurrentUserId();
        if(currentUserId.equalsIgnoreCase(user.getId())){
            return null;
        }
        String uuid = UUID.randomUUID().toString();

        ConnectionRequest connectionRequest = new ConnectionRequest(uuid,currentUserId,this.user.getDisplayName(),user.getName(), user.getId(),true);
        Query query = myRef.child(Constants.PENDINGCONNECTIONS.name().toLowerCase()).orderByChild("to").equalTo(user.getId()).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    myRef.child(Constants.PENDINGCONNECTIONS.name().toLowerCase()).child(uuid).setValue(connectionRequest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return status;
    }

    public MutableLiveData<List<ConnectionRequest>> getPendingRequests(String userId){
        MutableLiveData<List<ConnectionRequest>> connMutableLiveData = new MutableLiveData<>();

        Query query = myRef.child(Constants.PENDINGCONNECTIONS.name().toLowerCase()).orderByChild("to").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ConnectionRequest> tempList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ConnectionRequest connectionRequest = snapshot1.getValue(ConnectionRequest.class);
                    if(connectionRequest != null && connectionRequest.isPending()){
                        tempList.add(connectionRequest);
                    }
                }
                connMutableLiveData.setValue(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return connMutableLiveData;
    }

    public boolean acceptConnectionRequest(String connectionRequestId){
        myRef.child(Constants.PENDINGCONNECTIONS.name().toLowerCase()).child(connectionRequestId).child("pending").setValue(false);
        return true;
    }

    public MutableLiveData<List<ConnectionRequest>> getConnections(String userId){
        MutableLiveData<List<ConnectionRequest>> requestMutableLiveData = new MutableLiveData<>();

        Query query1 = myRef.child(Constants.PENDINGCONNECTIONS.name().toLowerCase()).orderByChild("to").equalTo(userId);
        Query query2 = myRef.child(Constants.PENDINGCONNECTIONS.name().toLowerCase()).orderByChild("from").equalTo(userId);

        List<ConnectionRequest> tempList = new ArrayList<>();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    ConnectionRequest connectionRequest = snapshot1.getValue(ConnectionRequest.class);
                    if(connectionRequest!= null && !connectionRequest.isPending()){
                        tempList.add(connectionRequest);
                    }
                }
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1: snapshot.getChildren()){
                            ConnectionRequest connectionRequest = snapshot1.getValue(ConnectionRequest.class);
                            if(connectionRequest!= null && !connectionRequest.isPending()){
                                tempList.add(connectionRequest);
                            }
                        }
                        requestMutableLiveData.setValue(tempList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return requestMutableLiveData;
    }
    public static void destroyInstance() {
        if(instance != null){
            instance = null;
        }
    }
}
