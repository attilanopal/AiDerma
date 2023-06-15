package com.example.aiderma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.aiderma.api.response.LoginResponse
import com.example.aiderma.databinding.ActivityLoginBinding
import com.example.aiderma.helper.SessionPref
import com.example.aiderma.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        lateinit var loginData : LoginResponse
        val pref = SessionPref(this)
        if (pref.isLoggedIn()){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        // when btnMasuk is clicked do errorhandling and run loginUser
        binding.btnMasuk.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pw = binding.edtPassword.text.toString()

            if (email.isEmpty() || pw.isEmpty()) {
                // Tampilkan pesan error jika email atau password kosong
                Toast.makeText(this, "Harap isi email dan password", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.loginUser(email, pw)
            }
        }

        // Observer
        loginViewModel.userLogin.observe(this){
            if (it?.token != null) {
                loginData = it
                Log.d("LoginActivity","userToken: ${loginData.token}")
                pref.loginUser(loginData) // Attaching token to Pref

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent) // go to Main Activity (logged In)
                finish()
            }
        }
    }
}