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
                R.id.home -> replaceFragment(Home())
                R.id.search -> replaceFragment(Jobs())
                R.id.wishlist -> replaceFragment(Notifications())
                R.id.profile -> replaceFragment(AppliedJobs())

                else -> {

                }

            }
            true
        }
    }




    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }
}