package com.example.expensemanagement;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class GoogleSignInDataStatic {

    private static GoogleSignInAccount googleSignInAccount;

    public static GoogleSignInAccount getAccount() {
        return googleSignInAccount;
    }

    public static void setAccount(GoogleSignInAccount account) {
        googleSignInAccount = account;
    }
}
