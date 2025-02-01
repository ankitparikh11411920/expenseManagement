package com.example.expensemanagement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanagement.Model.User;
import com.example.expensemanagement.R;
import com.example.expensemanagement.repository.ConnectionRepository;
import com.example.expensemanagement.repository.UserRepository;
import com.example.expensemanagement.viewModel.ConnectionViewModel;
import com.example.expensemanagement.viewModel.UserViewModel;

public class FindUsersActivity extends AppCompatActivity {


    private LinearLayout linearLayout;
    private EditText edt_email;
    private ImageView add_friend;
    private TextView username;
    private Button find;
    private UserViewModel userViewModel;
    private User foundUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_users);

        linearLayout = findViewById(R.id.view_match_findUsers);
        edt_email = findViewById(R.id.edt_email_findUsers);
        add_friend = findViewById(R.id.img_view_add_findUsers);
        username = findViewById(R.id.tv_username_findUsers);
        find = findViewById(R.id.btn_find_user_findUsers);

        ConnectionViewModel connectionViewModel = new ViewModelProvider(this).get(ConnectionViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        find.setOnClickListener(view -> {
            String email = edt_email.getText().toString().trim().toLowerCase();
            userViewModel.findUser(email);
            userViewModel.getUserLiveData().observe(FindUsersActivity.this, user -> {
                if (user == null || user.getName() == null || user.getId().equals(UserRepository.getInstance().getCurrentUserId())) return;
                foundUser = user;
                linearLayout.setVisibility(View.VISIBLE);
                username.setText(user.getName());
            });
        });

        add_friend.setOnClickListener(view -> {
            //TODO send connection request
            connectionViewModel.sendConnectionRequest(foundUser);
        });
    }
}