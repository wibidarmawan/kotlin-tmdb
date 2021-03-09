package com.example.mvvmtraining.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmtraining.api.RetrofitInstance
import com.example.mvvmtraining.model.LoginResponseModel
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LoginViewModel: ViewModel() {

    private val loginResponseModel = MutableLiveData<LoginResponseModel>()
    private val errorListener = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    fun login(email: String, password: String){
        compositeDisposable.add(RetrofitInstance.apiInterface.login(email, password)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<LoginResponseModel>(){
                override fun onSuccess(t: LoginResponseModel) {
                    if("Success" == t.status){
                        loginResponseModel.value = t
                    }else{
                        errorListener.value = true
                    }
                }

                override fun onError(e: Throwable) {
                    errorListener.value = true
                }

            }))
    }

    fun validate (field: String, layout: TextInputLayout): Boolean{
        if (field.isEmpty()){
            layout.setErrorEnabled(true)
            layout.setError("Email Can't Be Empty")
            return false
        } else{
            layout.setErrorEnabled(false)
            return true
        }
    }

    fun getLoginResponseModel(): MutableLiveData<LoginResponseModel>{
        return loginResponseModel
    }

    fun getErrorListener(): MutableLiveData<Boolean>{
        return errorListener
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}