package com.example.mobdev21_night_at_the_museum.presentation.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.domain.model.User
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity

class CheckPrefActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.check_pref_activity)
        super.onCreate(savedInstanceState)
        if (AppPreferences.getLoginInfo()) {
            newLogin()
        } else {
            startActivity(Intent(this, IntroductionActivity::class.java))
            finish()
        }
    }

    private fun newLogin() {
        val email = AppPreferences.getUserInfo().email
        val password = AppPreferences.getUserInfo().password
        val userRef = Firebase.firestore.collection("users")
        userRef.whereEqualTo("email", email).get()
            .addOnSuccessListener { query ->
                if (query.isEmpty || query.documents.isEmpty()) {
                    onLoginFail()
                    return@addOnSuccessListener
                }
                for (document in query.documents) {
                    val user = document.toObject(User::class.java)
                    if (user != null && user.email == email && user.password == password) {
                        user.key = document.id
                        onLoginSuccess(user)
                    } else {
                        onLoginFail()
                    }
                }
            }
            .addOnFailureListener {
                onLoginFail()
            }
    }

    private fun onLoginFail() {
        AppPreferences.clearLoginInfo()
        startActivity(Intent(this@CheckPrefActivity, IntroductionActivity::class.java))
        finish()
    }

    private fun onLoginSuccess(user: User) {
        setAppPreference(user)
        startActivity(Intent(this@CheckPrefActivity, RealMainActivity::class.java))
        finish()
    }

    private fun setAppPreference(user: User) {
        AppPreferences.setUserInfo(user)
        AppPreferences.saveLoginInfo()
    }
}
