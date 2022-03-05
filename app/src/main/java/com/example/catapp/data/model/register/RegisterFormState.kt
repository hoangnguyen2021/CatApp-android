package com.example.catapp.data.model.register

/**
 * Data validation state of the register form.
 */
data class RegisterFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val displayNameError: Int? = null,
    val isDataValid: Boolean = false
)