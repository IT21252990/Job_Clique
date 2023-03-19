package com.example.jobclique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jobclique.databinding.ActivityMainEmployerBinding

class MainActivityEmployer : AppCompatActivity() {

    private lateinit var binding : ActivityMainEmployerBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainEmployerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.emphome -> replaceFragment(HomeEmployer())
                R.id.empPosts -> replaceFragment(Jobs())
                R.id.empApplications -> replaceFragment(EmployerJobApplications())
                R.id.empprofile -> replaceFragment(ProfileEmployer())

                else -> {

                }

            }
            true
        }
    }




    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_employer,fragment)
        fragmentTransaction.commit()

    }
}