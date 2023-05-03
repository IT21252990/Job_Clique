package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class userLoginActivity : AppCompatActivity() {

    private lateinit var btngotoSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        btngotoSignUp = findViewById(R.id.btngotoSignUp)


        btngotoSignUp.setOnClickListener {
            var intent = Intent(this, userRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}