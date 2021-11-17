package com.example.mychallenge.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mychallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_main)
        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
    }
}