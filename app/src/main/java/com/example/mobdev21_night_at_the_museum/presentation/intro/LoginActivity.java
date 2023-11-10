package com.example.mobdev21_night_at_the_museum.presentation.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobdev21_night_at_the_museum.R;
import com.example.mobdev21_night_at_the_museum.presentation.home.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Authentication";
    private static final String PREF_NAME = "MUSEUM";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PASSWORD = "user_password";
    private final String TOKEN_ID = "792900083869-59hul96s0k7e4t55k6pi15mapiicdqn9.apps.googleusercontent.com";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean show = false;
    private EditText mEmail, mPassword;
    private LinearLayout googleSignIn;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro2_login_fragment);
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.etvLoginEmail);
        mPassword = findViewById(R.id.etvLoginPassword);
        googleSignIn = findViewById(R.id.llLoginGoogle);
        register = findViewById(R.id.tvLoginSignUp);

        // Initialize Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(TOKEN_ID)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Google Sign-In button click
        googleSignIn.setOnClickListener(v -> startGoogleSignIn());

        // Email and password login button click
        findViewById(R.id.btnLogin).setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            signInWithEmailAndPassword(email, password);
        });

        // Register button click
        register.setOnClickListener(v -> toRegister());
        FirebaseUser currentUser = mAuth.getCurrentUser();

        automaticSignIn();
        Log.d("SharedPreferences", "Auto Login");

    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                saveUserLoginData(email, password);
                Log.d("SharedPreferences", "Saved User Info");
                toHomeScreen();
            } else {
                toIntroduction();
                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startGoogleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mGoogleSignInClient.revokeAccess();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(data);
        }
    }

    private void handleGoogleSignInResult(Intent data) {
        Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = signInTask.getResult(ApiException.class);
            String email = account.getEmail();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(email);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuthWithGoogle(account);
                }
            });
        } catch (ApiException e) {
            Log.w(TAG, "Google sign in failed", e);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, authResultTask -> {
            if (authResultTask.isSuccessful()) {
                if (authResultTask.getResult().getAdditionalUserInfo().isNewUser()) {
                    createNewUserInFirestore();
                }
                toHomeScreen();
            } else {
                toIntroduction();
                Log.w(TAG, "signInWithCredential:failure", authResultTask.getException());
            }
        });
    }

    private void createNewUserInFirestore() {
        FirebaseUser user = mAuth.getCurrentUser();
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
                    toLogin();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing document", e);
                    // Handle the Firestore write failure
                });
    }

    private void toRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void toHomeScreen() {
        Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeScreen);
    }

    private void toLogin() {
        Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(toLogin);
        finish();
    }

    private void toIntroduction() {
        Intent toMainScreen = new Intent(getApplicationContext(), IntroductionActivity.class);
        startActivity(toMainScreen);
        finish();
    }

    public void saveUserLoginData(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PASSWORD, password);
        editor.apply();

    }

    public void automaticSignIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_USER_EMAIL, null);
        String password = sharedPreferences.getString(KEY_USER_PASSWORD, null);
        Log.d("PreferenceEmail", email);
        Log.d("PreferPass", password);
        if (email == null || password == null) {
            return;
        }
        signInWithEmailAndPassword(email, password);
    }


    public void clearUserLoginData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_EMAIL);
        editor.remove(KEY_USER_PASSWORD);
        editor.apply();
    }
}
