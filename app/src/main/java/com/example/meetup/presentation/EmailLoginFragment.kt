package com.example.meetup.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meetup.R
import com.example.meetup.databinding.FragmentEmailLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class EmailLoginFragment : Fragment() {
    private lateinit var binding: FragmentEmailLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email_login, container, false)
        mAuth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupListeners() {
        binding.emailLoginEditText.addTextChangedListener(TextFieldValidation(binding.emailLoginEditText))
        binding.passwordLoginEditText.addTextChangedListener(TextFieldValidation(binding.passwordLoginEditText))
        binding.createNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_emailLoginFragment_to_registrationFragment)
        }
        binding.signinBtn.setOnClickListener {
            if (isValidEmail() and isValidPassword()) {
                signInWithFirebase()
            }
        }
    }

    private fun signInWithFirebase() {
        val password: String = binding.passwordLoginTextInput.editText?.text.toString()
        val email: String = binding.emailLoginTextInput.editText?.text.toString()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().graph.startDestination = R.id.recyclerViewFragment
                    findNavController().navigate(findNavController().graph.startDestination)
                  // findNavController().navigate(R.id.action_emailLoginFragment_to_recyclerViewFragment)
                    Log.d("###", "signInWithEmail:success")
                } else {
                    Log.w("###", "signInWithEmail:failure", task.exception)
                }
            }
    }

    private fun isValidPassword(): Boolean {
        val password: String = binding.passwordLoginTextInput.editText?.text.toString()
        val passwordValidationRegex = "^" + "(?=.*[0-9])" + //at least 1 digit
                "(?=.*[a-zA-Z])" + //any letter
                ".{6,8}" +  // between 6 and 8 digits
                "$"

        return when {
            password.isEmpty() -> {
                binding.passwordLoginTextInput.error =
                    getString(R.string.erro_field_cannot_be_empty)
                false
            }
            password.length < 3 -> {
                binding.passwordLoginTextInput.error = getString(R.string.erro_password_too_short)
                false
            }
            password.length > 8 -> {
                binding.passwordLoginTextInput.error = getString(R.string.erro_password_too_long)
                false
            }
            !password.matches(passwordValidationRegex.toRegex()) -> {
                binding.passwordLoginTextInput.error = getString(R.string.erro_password_conditions)
                false
            }
            else -> {
                binding.passwordLoginTextInput.error = null
                binding.passwordLoginTextInput.isErrorEnabled = false
                true
            }
        }
    }

    private fun isValidEmail(): Boolean {

        val email: String = binding.emailLoginTextInput.editText?.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return when {
            email.isEmpty() -> {
                binding.emailLoginTextInput.error = getString(R.string.erro_field_cannot_be_empty)
                false
            }
            !email.matches(emailPattern.toRegex()) -> {
                binding.emailLoginTextInput.error = getString(R.string.erro_invalid_email_adress)
                false
            }
            else -> {
                binding.emailLoginTextInput.error = null
                binding.emailLoginTextInput.isErrorEnabled = false
                true
            }
        }
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.email_login_editText -> isValidEmail()
                R.id.password_login_editText -> isValidPassword()
            }
        }
    }

//    private fun emailErrorDialog(): Boolean {
//        val email: String = binding.emailLoginTextInput.editText?.text.toString()
//        val sharedPreferences =
//            requireActivity().getSharedPreferences(email, Context.MODE_PRIVATE)
//        val savedEmail =
//            sharedPreferences.getString("EMAIL", "")
//
//        return when {
//            email != savedEmail -> {
//                val emailErrorDialog = AlertDialog.Builder(context)
//                    .setTitle("Unregistred Email!")
//                    .setMessage("Create an account ?")
//                    .setIcon(R.drawable.ic_error)
//                    .setPositiveButton("Yes") { _, _ ->
//                        findNavController().navigate(R.id.action_emailLoginFragment_to_registrationFragment)
//                    }
//                    .setNegativeButton("No") { _, _ ->
//                    }.create()
//                emailErrorDialog.show()
//                false
//            }
//            else -> {
//                true
//            }
//        }
//    }

}