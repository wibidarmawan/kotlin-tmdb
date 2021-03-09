package com.example.mvvmtraining.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmtraining.R
import com.example.mvvmtraining.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel by lazy{
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener{
            registerViewModel.register(et_email_register.text.toString(), et_name_register.text.toString()
            , et_dob_register.text.toString(), et_address_register.text.toString(), et_password_register.text.toString())
        }

        setObserver()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setObserver(){
        registerViewModel.getLoginResponseModel().observe(this, Observer{
            if (it != null){
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })

        registerViewModel.getErrorListener().observe(this, Observer{
            if (it){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}