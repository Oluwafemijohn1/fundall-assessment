package com.example.fundallassessment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            // Start the main activity after the delay
            startActivity(Intent(this, MainAppActivity::class.java))
            finish() // Close the splash activity
        }, 5000)


    }
}

