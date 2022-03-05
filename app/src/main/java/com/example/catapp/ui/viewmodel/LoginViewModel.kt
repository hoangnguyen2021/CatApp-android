package com.example.catapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.catapp.R
import com.example.catapp.data.model.login.LoggedInUserView
import com.example.catapp.data.model.login.LoginFormState
import com.example.catapp.data.model.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser: LiveData<FirebaseUser> = _currentUser

    fun login(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser!!
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = user.displayName!!))
                    _currentUser.value = user
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}