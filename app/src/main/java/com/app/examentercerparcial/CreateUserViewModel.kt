package com.app.examentercerparcial

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val model: LiveData<UiModel>
        get() = _model
    private val _model = MutableLiveData<UiModel>()

    sealed class UiModel {
        class SaveUser(val success: Boolean) : UiModel()
        object Loading : UiModel()
    }

    fun doSave(user: MainActivity.User?) {
        _model.value = UiModel.Loading
        val runnable = Runnable {
            if (user != null) {
                _model.value = UiModel.SaveUser(userRepository.saveUser(user.username, user.lastname, user.email))
            }
        }
        Handler().postDelayed(runnable, 3000)
    }
}