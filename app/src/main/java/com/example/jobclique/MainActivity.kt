package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jobclique.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun jobSeekerHome(view: View){
        var intent = Intent(this , JobSeekerAvtivity::class.java)
        startActivity(intent)
    }

    fun userRegistrationPage(view: View){
        var intent = Intent(this,userRegistrationActivity::class.java)
        startActivity(intent)
    }

    fun userLoginPage(view: View){
        var intent = Intent(this,userLoginActivity::class.java)
        startActivity(intent)
    }

    fun employerRegiPartTwo(view: View){
        var intent = Intent(this,EmployerRegistrationNextPart::class.java)
        startActivity(intent)
    }
}