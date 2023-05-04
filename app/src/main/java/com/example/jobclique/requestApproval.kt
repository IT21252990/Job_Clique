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


        val logout: Button = findViewById(R.id.adminLogout)
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, userLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}