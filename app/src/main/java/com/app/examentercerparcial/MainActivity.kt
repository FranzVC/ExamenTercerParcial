package com.app.examentercerparcial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.textservice.TextInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import java.net.UnknownServiceException

class MainActivity : AppCompatActivity() {

    lateinit var saveViewModel: CreateUserViewModel

    data class User(val username: String, val lastname: String, val email: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveViewModel = CreateUserViewModel(UserRepository())
        saveViewModel.model.observe(this, Observer(::updateUi))


        val username = et_name.text.toString()
        val lastname = et_lastName.text.toString()
        val email = et_email.text.toString()
        var currentUser: User?
        btn_save.setOnClickListener {
            currentUser = User(username, lastname, email)
            saveViewModel.doSave(currentUser)
        }
    }

    private fun updateUi(model: CreateUserViewModel.UiModel?) {
        loading_progress_bar.visibility =
            if (model is CreateUserViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is CreateUserViewModel.UiModel.SaveUser -> validarLogin(model.success)
        }
    }

    private fun validarLogin(resp: Boolean) {
        if (resp) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("User created successfully")
            builder.setPositiveButton("OK") { _, _ ->
            }
            builder.show()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Fail to create")
            builder.setPositiveButton("OK") { _, _ ->

            }
            builder.show()
        }
    }
}
