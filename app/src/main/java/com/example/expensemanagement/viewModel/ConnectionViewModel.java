package com.example.expensemanagement.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanagement.Model.ConnectionRequest;
import com.example.expensemanagement.Model.User;
import com.example.expensemanagement.repository.ConnectionRepository;

import java.util.List;

public class ConnectionViewModel extends AndroidViewModel {
    private ConnectionRepository connectionRepository;
    private LiveData<Boolean> status;
    private LiveData<List<ConnectionRequest>> connectionListLiveData;
    public ConnectionViewModel(@NonNull Application application) {
        super(application);
        connectionRepository = ConnectionRepository.getInstance();
    }

    public void sendConnectionRequest(User user){
        status = connectionRepository.sendConnectionRequest(user);
    }

    public void getPendingRequests(String userId){
        connectionListLiveData = connectionRepository.getPendingRequests(userId);
    }

    public LiveData<Boolean> getStatus() {
        return status;
    }

    public LiveData<List<ConnectionRequest>> getConnectionListLiveData() {
        return connectionListLiveData;
    }

    public boolean acceptConnectionRequest(String connectionRequestId){
        return connectionRepository.acceptConnectionRequest(connectionRequestId);
    }

    public LiveData<List<ConnectionRequest>> getConnections(String userId){
        return connectionRepository.getConnections(userId);
    }
}
