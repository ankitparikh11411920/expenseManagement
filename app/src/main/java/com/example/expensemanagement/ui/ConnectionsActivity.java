package com.example.expensemanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.expensemanagement.Model.ConnectionRequest;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapters.ConnectionRequestAdapter;
import com.example.expensemanagement.adapters.SimpleTextViewAdapter;
import com.example.expensemanagement.repository.ExpenseRepository;
import com.example.expensemanagement.viewModel.ConnectionViewModel;
import com.example.expensemanagement.viewModel.ExpenseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectionsActivity extends AppCompatActivity {

    private RecyclerView rv_allConnections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        rv_allConnections = findViewById(R.id.rv_allConnections_ConnectionsActivity);

        ConnectionViewModel connectionViewModel = new ViewModelProvider(this).get(ConnectionViewModel.class);
        String userId = ExpenseRepository.getInstance().getCurrentUserId();

        rv_allConnections.setLayoutManager(new LinearLayoutManager(this));
        SimpleTextViewAdapter simpleTextViewAdapter =  new SimpleTextViewAdapter(this, new ArrayList<>());
        rv_allConnections.setAdapter(simpleTextViewAdapter);
        LiveData<List<ConnectionRequest>> listLiveData = connectionViewModel.getConnections(userId);
        listLiveData.observe(this, connectionRequests -> {
            List<String> items = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                items = connectionRequests.stream()
                        .map(connectionRequest -> {
                            if(userId.equalsIgnoreCase(connectionRequest.getFrom())){
                                return connectionRequest.getToName();
                            }else{
                                return connectionRequest.getFromName();
                            }
                        }).collect(Collectors.toList());
            }
            simpleTextViewAdapter.setItems(items);
        });
    }
}