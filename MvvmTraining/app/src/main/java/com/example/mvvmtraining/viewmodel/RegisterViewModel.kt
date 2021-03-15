package com.example.mvvmtraining.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmtraining.api.RetrofitInstance
import com.example.mvvmtraining.model.LoginResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RegisterViewModel: ViewModel() {

    private val loginResponseModel = MutableLiveData<LoginResponseModel>()
    private val emailExist = MutableLiveData<Boolean>()
    private val errorListener = MutableLiveData<Boolean>()
    private val minimalPassword = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    fun validateEmail(email: String){
        compositeDisposable.add(RetrofitInstance.apiInterface.getEmail()
            .flatMapIterable { it }
            .contains(email)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    emailExist.value = it
                },
                {
                    Log.e("RegisterViewModel", it.message.toString())
                }
            ))
    }

    fun register(email: String, name: String, dob: String, address: String, password: String){
        compositeDisposable.add(RetrofitInstance.apiInterface.register(email, name, dob, address, password)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<LoginResponseModel>(){
                override fun onSuccess(t: LoginResponseModel) {
                    if("Success" == t.status){
                        loginResponseModel.value = t
                    } else{
                        errorListener.value = true
                    }
                }

                override fun onError(e: Throwable) {
                    errorListener.value = true
                }

            }))
    }

    fun showPasswordMinimalAlert(password: String){
        minimalPassword.value = password.length < 6
    }

    fun getMinimalPassword(): MutableLiveData<Boolean>{
        return minimalPassword
    }

    fun getLoginResponseModel(): MutableLiveData<LoginResponseModel>{
        return loginResponseModel
    }

    fun emailExist(): MutableLiveData<Boolean>{
        return emailExist
    }

    fun getErrorListener(): MutableLiveData<Boolean>{
        return errorListener
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}