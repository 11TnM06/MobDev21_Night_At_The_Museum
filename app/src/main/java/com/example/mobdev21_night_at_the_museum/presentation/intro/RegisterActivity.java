package com.example.mobdev21_night_at_the_museum.presentation.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmail, mPassword, mRePassword;
    private TextView toLogin;
    private static final String TAG = "Authentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro3_signup_activity);
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.etvSignUpEmail);
        mPassword = findViewById(R.id.etvSignUpPassword);
        mRePassword = findViewById(R.id.etvSignUpPasswordAgain);
        toLogin = findViewById(R.id.tvSignUpSignIn);

        toLogin.setOnClickListener(view -> openLoginActivity());

        findViewById(R.id.btnSignUp).setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String rePassword = mRePassword.getText().toString();

            if (!rePassword.equals(password)) {
                Toast.makeText(getApplicationContext(), "Password and Re-Password do not match", Toast.LENGTH_SHORT).show();
                mRePassword.requestFocus();
            } else {
                createAccount(email, password);
            }
        });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                writeUserDataToFirestore(user);
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeUserDataToFirestore(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> userData = new HashMap<>();
        userData.put("avatar", null);
        userData.put("fcollections", null);
        userData.put("fitems", null);
        userData.put("fstories", null);
        userData.put("name", null);

        db.collection("users").document(user.getEmail())
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                    openLoginActivity();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing document", e);
                    // Handle the Firestore write failure
                });
    }

    private void openLoginActivity() {
        Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(toLogin);
        finish();
    }
}
