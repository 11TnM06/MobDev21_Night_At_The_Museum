package com.example.mobdev21_night_at_the_museum.presentation.intro

import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumActivity
import com.example.mobdev21_night_at_the_museum.common.extension.getAppFont
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.SignUpActivityBinding
import com.example.mobdev21_night_at_the_museum.domain.model.User
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.dialog.LoadingDialog2


class SignUpActivity : MuseumActivity<SignUpActivityBinding>(R.layout.sign_up_activity) {

    override fun onInitView() {
        super.onInitView()
        initOnClick()
    }

    private fun initOnClick() {
        setPasswordEditText(binding.etvSignUpPassword, binding.ivSignUpShowPassword)
        setPasswordEditText(binding.etvSignUpPasswordAgain, binding.ivSignUpShowPasswordAgain)
        binding.btnSignUp.setOnSafeClick { signUp() }
        binding.tvSignUpSignIn.setOnSafeClick { navigateTo(LoginActivity::class.java) }
        binding.etvSignUpPasswordAgain.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signUp()
                true
            } else {
                false
            }
        }
    }

    private fun setPasswordEditText(editText: EditText, imageView: ImageView) {
        imageView.setOnSafeClick {
            if (editText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                hidePassword(editText, imageView)
            } else {
                showPassword(editText, imageView)
            }
        }
    }

    private fun hidePassword(editText: EditText, imageView: ImageView) {
        editText.apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setSelection(length())
            typeface = getAppFont(R.font.roboto_regular)
        }
        imageView.setImageResource(R.drawable.ic_show_password_gray)
    }

    private fun showPassword(editText: EditText, imageView: ImageView) {
        editText.apply {
            inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            setSelection(length())
        }
        imageView.setImageResource(R.drawable.ic_hide_password_gray)
    }

    private fun signUp() {
        val email = binding.etvSignUpEmail.text.toString()
        val password = binding.etvSignUpPassword.text.toString()
        val passwordAgain = binding.etvSignUpPasswordAgain.text.toString()

        // check if email is empty
        if (email.isEmpty()) {
            toast(getAppString(R.string.email_empty))
            return
        }

        // check if password is empty
        if (password.isEmpty() || passwordAgain.isEmpty()) {
            toast(getAppString(R.string.password_empty))
            return
        }

        // check if password is match
        if (password != passwordAgain) {
            toast(getAppString(R.string.password_not_match))
            return
        }

        // show loading dialog
        val LoadingDialog2 = LoadingDialog2()
        LoadingDialog2.show(supportFragmentManager, LoadingDialog2::class.java.simpleName)

        // check if email is already registered
        val userRef = Firebase.firestore.collection("users")
        userRef.whereEqualTo("email", email).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.documents.isNotEmpty()) {
                    LoadingDialog2.dismiss()
                    toast(getAppString(R.string.account_exist))
                } else {
                    addUserToDatabase(User(email = email, password = password), LoadingDialog2)
                }
            }
            .addOnFailureListener {
                LoadingDialog2.dismiss()
                toast(getAppString(R.string.sign_up_fail) + ": ${it.message}")
            }
    }

    private fun addUserToDatabase(user: User, dialog: LoadingDialog2) {
        val ref = Firebase.firestore.collection("users")
        ref.add(user)
            .addOnSuccessListener {
                actionOnSuccess(user.apply { key = it.id }, dialog)
            }
            .addOnFailureListener {
                actionOnFailure(dialog, it.message ?: "")
            }
    }

    private fun setAppPreference(user: User) {
        AppPreferences.setUserInfo(user)
        AppPreferences.saveLoginInfo()
    }

    private fun actionOnSuccess(user: User, dialog: LoadingDialog2) {
        dialog.dismiss()
        toast(getAppString(R.string.sign_up_success))
        setAppPreference(user)
        navigateToAndClearStack(RealMainActivity::class.java)
    }


    private fun actionOnFailure(dialog: LoadingDialog2, message: String = "") {
        dialog.dismiss()
        toast(getAppString(R.string.sign_up_fail) + ": $message")
    }
}
