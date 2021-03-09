package com.example.mvvmtraining.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmtraining.R
import com.example.mvvmtraining.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by lazy{
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener{
            if (loginViewModel.validate(et_email.text.toString(), til_email)
                    && loginViewModel.validate(et_password.text.toString(), til_password)){
                loginViewModel.login(et_email.text.toString(), et_password.text.toString())
            }
        }

        tv_register.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        setObserver()
    }

    private fun setObserver(){
        loginViewModel.getLoginResponseModel().observe(this, Observer{
            if(it != null){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })

        loginViewModel.getErrorListener().observe(this, Observer{
            if(it){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    private fun login(email: String = "wibid26@gmail.com", password: String): String{
//        return password
//    }
}