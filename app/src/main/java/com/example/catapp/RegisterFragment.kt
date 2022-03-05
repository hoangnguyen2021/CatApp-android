package com.example.catapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.catapp.data.model.register.RegisteredUserView
import com.example.catapp.databinding.FragmentRegisterBinding
import com.example.catapp.ui.viewmodel.RegisterViewModel
import com.example.catapp.ui.viewmodel.RegisterViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    lateinit var registerViewModel: RegisterViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel =
            ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val displayNameEditText = binding.displayName
        val registerButton = binding.register
        val loadingProgressBar = binding.loading

        registerViewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { registerFormState ->
                if (registerFormState == null) {
                    return@Observer
                }
                // enable/disable register button accordingly
                registerButton.isEnabled = registerFormState.isDataValid
                // show error if invalid
                registerFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                registerFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
                registerFormState.displayNameError?.let {
                    displayNameEditText.error = getString(it)
                }
            })

        registerViewModel.registerResult.observe(viewLifecycleOwner,
            Observer { registerResult ->
                registerResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                registerResult.error?.let {
                    showLoginFailed(it)
                }
                registerResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        // create and add text changed listener to edit texts
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                registerViewModel.registerDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                    displayNameEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        displayNameEditText.addTextChangedListener(afterTextChangedListener)

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerViewModel.register(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                    displayNameEditText.text.toString()
                )
            }
            false
        }

        registerButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            registerViewModel.register(
                usernameEditText.text.toString(),
                passwordEditText.text.toString(),
                displayNameEditText.text.toString()
            )
        }
    }

    private fun updateUiWithUser(model: RegisteredUserView) {
        val welcome = "Account ${model.displayName} created successfully!"
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}