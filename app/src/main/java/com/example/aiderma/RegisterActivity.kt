package com.example.aiderma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.aiderma.databinding.ActivityRegisterBinding
import com.example.aiderma.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    // private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // When btnDaftar is pressed do errorHandling and registerUser function
        binding.btnDaftar.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val nama = binding.edtNama.text.toString()
            val tanggal_lahir = binding.edtTglLahir.text.toString()
            val pw = binding.edtPassword.text.toString()
            val confirmPw = binding.edtConfirmPassword.text.toString()


            // ErrorHandling
            if (email.isEmpty() || nama.isEmpty() || tanggal_lahir.isEmpty() || pw.isEmpty() || confirmPw.isEmpty()) {
                // Tampilkan pesan error jika ada kolom yang kosong
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            } else if (!email.endsWith("@gmail.com")) {
                // Tampilkan pesan error jika email tidak diakhiri dengan "@gmail.com"
                Toast.makeText(this, "Email harus diakhiri dengan @gmail.com", Toast.LENGTH_SHORT).show()
            } else if (pw != confirmPw) {
                // Tampilkan pesan error jika password tidak cocok dengan konfirmasi password
                Toast.makeText(this, "Password dan Konfirmasi Password tidak cocok", Toast.LENGTH_SHORT).show()
            } else if (!tanggal_lahir.matches("\\d{4}-\\d{2}-\\d{2}".toRegex())) {
                // Tampilkan pesan error jika tanggal lahir tidak mengikuti format yyyy-mm-dd
                Toast.makeText(this, "Format Tanggal Lahir harus yyyy-mm-dd", Toast.LENGTH_SHORT).show()
            } else {
                // Panggil registerUser jika semua kriteria terpenuhi
                registerViewModel.registerUser(email, pw, nama, tanggal_lahir)
            }
        }

        registerViewModel.userRegister.observe(this){
            if(it?.insertId != null) {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}