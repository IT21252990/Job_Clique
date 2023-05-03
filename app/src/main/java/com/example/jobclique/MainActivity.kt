package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.view.View

import androidx.fragment.app.Fragment
import com.example.jobclique.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var logout: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout = findViewById(R.id.btnLogouttest)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(this, userLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        var btnJobApplication = findViewById<Button>(R.id.btnJobApplication)
        btnJobApplication.setOnClickListener {
            var intent = Intent(this, JobApplication::class.java)
            startActivity(intent)
            finish()
        }


        var jobSeekerHome = findViewById<Button>(R.id.btnjobSeekerMenu)
        jobSeekerHome.setOnClickListener {
            var intent = Intent(this, JobSeekerAvtivity::class.java)
            startActivity(intent)
            finish()
        }

        var employerHome = findViewById<Button>(R.id.btnEmployer)
        employerHome.setOnClickListener {
            var intent = Intent(this, MainActivityEmployer::class.java)
            startActivity(intent)
            finish()
        }

        var userRegistrationPage = findViewById<Button>(R.id.btnRegister)
        userRegistrationPage.setOnClickListener {
            var intent = Intent(this, userRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        var userLoginPage = findViewById<Button>(R.id.btnLogin)
        userLoginPage.setOnClickListener {
            var intent = Intent(this, userLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        var employerRegiPartTwo = findViewById<Button>(R.id.btnEmpRegiPart2)
        employerRegiPartTwo.setOnClickListener {
            var intent = Intent(this, EmployerRegistrationNextPart::class.java)
            startActivity(intent)
            finish()
        }

        var ReqApproval = findViewById<Button>(R.id.btnAdminMenu)
        ReqApproval.setOnClickListener {
            var intent = Intent(this, requestApproval::class.java)
            startActivity(intent)
            finish()
        }
    }
}