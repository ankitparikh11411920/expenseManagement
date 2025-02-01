package com.example.expensemanagement.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.Model.User;
import com.example.expensemanagement.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<User> userLiveData;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public void findUser(String email){
        userLiveData = userRepository.findUser(email);
    }

    public boolean addFriend(User user){
        return userRepository.addFriend(user);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
