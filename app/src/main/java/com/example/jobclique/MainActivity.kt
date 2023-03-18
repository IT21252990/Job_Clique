package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.jobclique.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnJobSeekerHome = findViewById<Button>(R.id.btnJobSeekerHome)
        btnJobSeekerHome.setOnClickListener {
            var intent = Intent(this,MainActivityJobApplication::class.java)
            startActivity(intent)
            finish()
        }

        var btnJobApplication = findViewById<Button>(R.id.btnJobApplication)
        btnJobApplication.setOnClickListener {
            var intent = Intent(this,JobApplication::class.java)
            startActivity(intent)
            finish()
        }

    }




}