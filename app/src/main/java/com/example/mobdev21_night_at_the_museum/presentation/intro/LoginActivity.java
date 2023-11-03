package com.example.mobdev21_night_at_the_museum.presentation.intro;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
/*
@TODO Facebook Login
 */
public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    MaterialCardView loginWithEmailAndPassword;
    private static final String TAG = "Authentication";
    private final String TOKEN_ID = "792900083869-59hul96s0k7e4t55k6pi15mapiicdqn9.apps.googleusercontent.com";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private LinearLayout googleSignIn, FacebookSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    boolean show;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro2_login_fragment);

        show = false;
        register = findViewById(R.id.tvLoginSignUp);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();

        }
        mEmail = findViewById(R.id.etLoginEmail);
        mPassword = findViewById(R.id.etLoginPassword);
        FacebookSignIn = findViewById(R.id.llLoginFb);
        googleSignIn = findViewById(R.id.llLoginGoogle);

        //GOOGLE_AUTH_INIT
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(TOKEN_ID)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                mGoogleSignInClient.revokeAccess();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        //EMAIL AND PASSWORD
        loginWithEmailAndPassword = findViewById(R.id.btnLogin);
        loginWithEmailAndPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(mEmail.getText());
                String password = String.valueOf(mPassword.getText());
                signInWithEmailAndPassword(email, password);
            }
        });
        //To Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent toMainScreen = new Intent(getApplicationContext(), IntroductionActivity.class);
            startActivity(toMainScreen);
            finish();
        }
    }

    //COMMON FUNCTION
    public void redirectToLogin() {
        Intent toMainScreen = new Intent(getApplicationContext(), IntroductionActivity.class);
        startActivity(toMainScreen);
        finish();
    }

    //SIGN_IN WITH EMAIL AND PASSWORD
    private void signInWithEmailAndPassword(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    redirectToLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void showPassword(View view) {
        if (!show) {
            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            show = true;
        } else {
            mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            show = false;
        }
    }

    //GOOGLE_SIGN_IN
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                        Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                    }
                    redirectToLogin();
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
            }
        });
    }

    private void toRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

}
