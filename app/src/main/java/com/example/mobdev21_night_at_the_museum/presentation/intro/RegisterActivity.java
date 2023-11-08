package com.example.mobdev21_night_at_the_museum.presentation.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mEmail, mPassword, mRePassword;
    com.google.android.material.card.MaterialCardView register;
    private static final String TAG = "Authentication";
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro3_signup_activity);
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.etSignupEmail);
        mPassword = findViewById(R.id.etSignupPassword);
        mRePassword = findViewById(R.id.etSignupPasswordAgain);
        register = findViewById(R.id.btnSignUp);
        login = findViewById(R.id.tvLoginSignUp);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(toLogin);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(mEmail.getText()).toString();
                String password = Objects.requireNonNull(mPassword.getText()).toString();
                String rePassword = Objects.requireNonNull(mRePassword.getText().toString());
                if (!rePassword.equals(password)) {
                    Toast.makeText(getApplicationContext(), "Password and Re-Password does not match", Toast.LENGTH_SHORT).show();
                    mRePassword.requestFocus();
                } else {
                    createAccount(email, password);
                }
            }
        });
    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(toLogin);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        // [END create_user_with_email]
    }


}