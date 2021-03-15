package com.example.mvvmtraining.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmtraining.R
import com.example.mvvmtraining.viewmodel.RegisterViewModel
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            registerViewModel.register(
                et_email_register.text.toString(),
                et_name_register.text.toString(),
                et_dob_register.text.toString(),
                et_address_register.text.toString(),
                et_password_register.text.toString()
            )
        }

        et_email_register.textChanges()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .map {
                it.trim().toString()
            }
            .filter{
                it.length > 3
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                registerViewModel.validateEmail(it)
            }, {
                Log.e("RegisterActivity", it.message.toString())
            }
            )

        et_password_register.textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .map {
                    it.trim().toString()
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            registerViewModel.showPasswordMinimalAlert(it)
                        },
                        {
                            Toast.makeText(this, "error testoh", Toast.LENGTH_SHORT).show()
                        }
                )

        setObserver()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setObserver() {
        registerViewModel.getLoginResponseModel().observe(this, Observer {
            if (it != null) {
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })

        registerViewModel.getErrorListener().observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })

        registerViewModel.emailExist().observe(this, Observer {
            if (it) {
                til_email_register.setErrorEnabled(true)
                til_email_register.setError("Email already exist! Choose another email!")
                btn_register.setEnabled(false)
            } else {
                til_email_register.setErrorEnabled(false)
                btn_register.setEnabled(true)
            }
        })

        registerViewModel.getMinimalPassword().observe(this, Observer{
            if (it){
                til_password_register.setErrorEnabled(true)
                til_password_register.setError("Password must be at least 6 characters")
            } else{
                til_password_register.setErrorEnabled(false)
            }
        })
    }
}