package com.example.catapp.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.R
import com.example.catapp.data.model.register.RegisterFormState
import com.example.catapp.data.model.register.RegisterResult
import com.example.catapp.data.model.register.RegisteredUserView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterViewModel(private val auth: FirebaseAuth) : ViewModel() {
    // register form validation
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    // register result
    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    /**
     *  Sign up a new Firebase user.
     *
     *  @param username new user's username
     *  @param password new user's password
     *  @param displayName new user's display name
     */
    fun register(username: String, password: String, displayName: String) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser!!
                    // update _registerResult
                    _registerResult.value =
                        RegisterResult(success = RegisteredUserView(displayName = displayName))
                    // update user's profile with the display name
                    val profileUpdates = userProfileChangeRequest {
                        this.displayName = displayName
                    }
                    user.updateProfile(profileUpdates)
                } else {
                    _registerResult.value = RegisterResult(error = R.string.register_failed)
                }
            }
    }

    /**
     *  Validate inputs for register form.
     *
     *  @param username new user's username
     *  @param password new user's password
     *  @param displayName new user's display name
     */
    fun registerDataChanged(username: String, password: String, displayName: String) {
        // update _registerForm
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isDisplayNameValid(displayName)) {
            _registerForm.value = RegisterFormState(displayNameError = R.string.invalid_display_name)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    /**
     *  Validate username input.
     *
     *  @param username new user's username
     *  @return true if valid
     */
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    /**
     *  Validate password input.
     *
     *  @param password new user's password
     *  @return true if valid
     */
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    /**
     *  Validate display name input.
     *
     *  @param displayName new user's password
     *  @return true if valid
     */
    private fun isDisplayNameValid(displayName: String): Boolean {
        return displayName.length > 5
    }
}