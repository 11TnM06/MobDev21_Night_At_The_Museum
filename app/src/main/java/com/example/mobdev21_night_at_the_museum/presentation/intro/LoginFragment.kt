package com.example.mobdev21_night_at_the_museum.presentation.intro

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.LoginFragment2Binding
import com.example.mobdev21_night_at_the_museum.domain.model.User

class LoginFragment : MuseumFragment<LoginFragment2Binding>(R.layout.login_fragment_2) {

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? IntroductionActivity)?.setWhiteBackground()
        initOnClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (museumActivity as? IntroductionActivity)?.setIntroBackground()
    }

    private fun initOnClick() {
        binding.tvLoginForgotPassword.setOnSafeClick { }
        binding.llLoginFacebook.setOnSafeClick { }
        binding.llLoginGoogle.setOnSafeClick { }
        binding.tvLoginSignUp.setOnSafeClick { }
        binding.btnLogin.setOnSafeClick {
            login(email = binding.etvLoginPassword.text.toString(), password = binding.etvLoginPassword.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        val query = ref.orderByChild("email").equalTo(email)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user?.email == "example@gmail.com") {
                        if (user.password == password) {
                            toast("Đăng nhập thành công")
                        } else {
                            toast("Sai mật khẩu")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                toast("Đăng nhập thất bại: ${error.message}")
            }
        })
    }

    private fun login2(email: String, password: String) {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("Users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val user = data.getValue(User::class.java)
                    if (user?.email == email) {
                        toast("Tìm thấy tài khoản ${user.password}")
                        if (user.password == password) {
                            toast("Đăng nhập thành công")
                        } else {
                            toast("Sai mật khẩu")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                toast("Lấy danh sách mô hình thất bại: ${error.message}")
            }
        })
    }
}
