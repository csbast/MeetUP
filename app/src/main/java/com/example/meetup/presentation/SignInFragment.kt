package com.example.meetup.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meetup.R
import com.example.meetup.databinding.FragmentLoginBinding
import com.example.meetup.extensions.showToast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment(R.layout.fragment_login) {

    private val callbackManager = CallbackManager.Factory.create()

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        const val GOOGLE_SIGN_IN = 1903
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = Firebase.auth
        mAuth.signOut()
        setupListeners()
    }

    private fun setupLoginFacebook() {
        val btnLoginFacebook = binding.fbLoginButton
        val btnLoginFacebookFake = binding.fbLoginButtonFake
        btnLoginFacebookFake.setOnClickListener {
            btnLoginFacebook.callOnClick()
        }
        btnLoginFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val token = loginResult.accessToken.token
                        val credential = FacebookAuthProvider.getCredential(token)
                        signInOnFirebase(credential)
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")
                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val idToken = account.idToken
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                signInOnFirebase(credential)
            } catch (e: ApiException) {
                Toast.makeText(context, "onActivityResult Exception", Toast.LENGTH_SHORT).show()
                Log.d("###", e.message.toString())
                Log.d("###", e.toString())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)//
        googleSignInClient = GoogleSignIn.getClient(requireContext(), getGSO())

    }

    private fun showGoogleSignInDialog() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    private fun getGSO(): GoogleSignInOptions {
        val clientId = "169900779180-t22ieegpib8g65i7cu00sk52sfmga9ui.apps.googleusercontent.com"
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
    }

    private fun setupListeners() {
        setupLoginFacebook()
        setupLoginEmail()
        setupLoginAnounymously()
        setupLoginGoogleButton()
        setupSignUoButton()
    }

    private fun setupSignUoButton() {
        binding.signUpTextView.setOnClickListener{
            findNavController().navigate(R.id.registrationFragment)
        }
    }

    private fun setupLoginAnounymously() {
        binding.loginAnonymously.setOnClickListener {
            signInAnonymously()
        }
    }

    private fun setupLoginEmail() {
        binding.loginEmailBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_emailLoginFragment)
        }
    }

    private fun setupLoginGoogleButton() {
        binding.googleButtonFake.setOnClickListener {
            showGoogleSignInDialog()
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_recyclerViewFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d("###", "FUNCIONA KRO MENU")
        menu.findItem(R.id.menu_logout).setVisible(false)
        Log.d("###", "FUNCIONA KRO MENU")
    }

    private fun signInAnonymously() {
        mAuth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().graph.startDestination = R.id.recyclerViewFragment
                    navigateToHome()
                } else {
                    Log.d("###", task.exception?.localizedMessage ?: "Error ao login anonnymously")
                }
            }
    }

    private fun signInOnFirebase(credential: AuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToHome()
                } else {
                    Log.d("###", "signInWithCredential:failure", task.exception)
                    requireContext().showToast("Login Failune\n${task.exception?.message.toString()}")
                }
            }
    }

}
