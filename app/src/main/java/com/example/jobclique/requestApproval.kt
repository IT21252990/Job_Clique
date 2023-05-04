package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class requestApproval : AppCompatActivity() {

    private lateinit var logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_approval)


        var adminLogout = findViewById<Button>(R.id.adminLogout)
        adminLogout.setOnClickListener {
            var intent = Intent(this, logoutAccount_Confirm::class.java)
            startActivity(intent)
            finish()
        }
    }
}