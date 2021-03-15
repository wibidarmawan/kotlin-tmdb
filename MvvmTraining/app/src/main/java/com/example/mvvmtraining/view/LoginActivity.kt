package com.example.mvvmtraining.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmtraining.R
import com.example.mvvmtraining.viewmodel.LoginViewModel
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by lazy{
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener{
            if (loginViewModel.validate(til_email)
                    && loginViewModel.validate(til_password)){
                loginViewModel.login(til_email.editText?.text.toString(), til_password.editText?.text.toString())
            }
        }

        tv_register.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
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
}
