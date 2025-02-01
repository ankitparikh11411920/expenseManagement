package com.example.expensemanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanagement.Model.ConnectionRequest;
import com.example.expensemanagement.R;
import com.example.expensemanagement.adapters.ConnectionRequestAdapter;
import com.example.expensemanagement.repository.ExpenseRepository;
import com.example.expensemanagement.viewModel.ConnectionViewModel;

import java.util.ArrayList;
import java.util.List;

public class PendingRequestActivity extends AppCompatActivity {

    private TextView tv_totalRequest;
    private RecyclerView rv_allRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);

        tv_totalRequest = findViewById(R.id.tv_total_requests_pendingRequest);
        rv_allRequests = findViewById(R.id.requestlist_pendingRequest);


        rv_allRequests.setLayoutManager(new LinearLayoutManager(this));

        List<ConnectionRequest> connectionRequestList = new ArrayList<>();
        ConnectionRequestAdapter connectionRequestAdapter = new ConnectionRequestAdapter(this,connectionRequestList);
        rv_allRequests.setAdapter(connectionRequestAdapter);

        ConnectionViewModel connectionViewModel = new ViewModelProvider(this).get(ConnectionViewModel.class);
        connectionViewModel.getPendingRequests(ExpenseRepository.getInstance().getCurrentUserId());
        connectionViewModel.getConnectionListLiveData().observe(this, list -> {
            connectionRequestAdapter.setConnectionRequestList(list);
            connectionRequestList.addAll(list);
        });

        connectionRequestAdapter.setOnClickListener(new ConnectionRequestAdapter.onItemClickListener() {
            @Override
            public void onAcceptClick(int position) {
                //TODO accept connection request, set IsPending to false
                Toast.makeText(PendingRequestActivity.this, "Accept", Toast.LENGTH_SHORT).show();
                connectionViewModel.acceptConnectionRequest(connectionRequestList.get(position).get_id());
            }

            @Override
            public void onRejectClick(int position) {
                //TODO reject connection request, remove node from database
                Toast.makeText(PendingRequestActivity.this, "Reject", Toast.LENGTH_SHORT).show();
            }
        });
    }
}