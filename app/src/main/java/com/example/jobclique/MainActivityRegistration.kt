package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivityRegistration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration)


        var btnjobseeker = findViewById<Button>(R.id.btnJobSeekerRegi)
        btnjobseeker.setOnClickListener {
            var intent = Intent(this, userRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        var btnEmployer = findViewById<Button>(R.id.btnEmployerRegi)
        btnEmployer.setOnClickListener {
            var intent = Intent(this, EmployerRegistrationNextPart::class.java)
            startActivity(intent)
            finish()
        }

    }
}