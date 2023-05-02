package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.view.View

import androidx.fragment.app.Fragment
import com.example.jobclique.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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

        var btnAppliedJobs = findViewById<Button>(R.id.btnAppliedJobs)
        btnAppliedJobs.setOnClickListener {
            var intent = Intent(this,MainActivityJobApplication::class.java)
            startActivity(intent)
            finish()
        }

        var btnAcceptApplications = findViewById<Button>(R.id.btnAcceptApplications)
        btnAcceptApplications.setOnClickListener {
            var intent = Intent(this,MainActivityEmployer::class.java)
            startActivity(intent)
            finish()
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

      fun employerHome(view: View){
          var intent = Intent(this,MainActivityEmployer::class.java)
          startActivity(intent)
      }

      fun ReqApproval(view: View){
          var intent = Intent(this, requestApproval::class.java)
          startActivity(intent)
      }

}