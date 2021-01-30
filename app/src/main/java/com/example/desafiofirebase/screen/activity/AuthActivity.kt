package com.example.desafiofirebase.screen.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiofirebase.databinding.ActivityAuthBinding
import com.example.desafiofirebase.provider.IAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var appAuth: IAuthProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun navigateUpToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}