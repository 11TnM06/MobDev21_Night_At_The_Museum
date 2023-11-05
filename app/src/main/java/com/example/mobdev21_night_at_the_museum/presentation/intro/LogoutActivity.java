package com.example.mobdev21_night_at_the_museum.presentation.intro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutActivity extends AppCompatActivity {
    Button logout;
    FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro1_introduction_activity);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null) {
            Intent auth = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(auth);
            finish();
        }

        signOut();
    }

    private void signOut() {

        FirebaseAuth.getInstance().signOut();

        Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(toLogin);
        finish();
    }

}