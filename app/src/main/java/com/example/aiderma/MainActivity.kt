package com.example.aiderma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aiderma.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide() // Hide actionbar (yg di atas)

        val navView : BottomNavigationView = binding.bottomNav

        val navController = findNavController(R.id.nav_host_fragment)
        // Mengoper tiap menu ID sebagai set of ID
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.bottom_home,R.id.bottom_faq,R.id.bottom_camera,R.id.bottom_about,R.id.bottom_profile
            )
        )

//        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}