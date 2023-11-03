package com.example.mobdev21_night_at_the_museum.presentation.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobdev21_night_at_the_museum.R;

public class IntroductionActivity extends AppCompatActivity {
    com.google.android.material.card.MaterialCardView login;
    com.google.android.material.card.MaterialCardView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro1_introduction_activity);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.signupBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(toLogin);
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent toSignUp = new Intent(getApplicationContext(), RegisterActivity.class);
            }
        });
    }
}