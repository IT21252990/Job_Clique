package com.example.jobclique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jobclique.databinding.ActivityMainBinding
import com.example.jobclique.databinding.ActivityMainEmployerBinding

class MainActivityEmployer : AppCompatActivity() {

    private lateinit var binding : ActivityMainEmployerBinding

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainEmployerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeJobs())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.jobsHome -> replaceFragment(HomeJobs())
                R.id.jobsPosts -> replaceFragment(PostsJobs())
                R.id.jobsNotify -> replaceFragment(NotificationsJobs())
                R.id.jobsProfile -> replaceFragment(ProfileJobs())

                else -> {

                }

            }
            true
        }

    }
}