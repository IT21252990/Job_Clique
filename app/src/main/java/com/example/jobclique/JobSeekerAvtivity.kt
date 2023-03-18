package com.example.jobclique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jobclique.databinding.ActivityJobSeekerAvtivityBinding

class JobSeekerAvtivity : AppCompatActivity() {

    lateinit var binding : ActivityJobSeekerAvtivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobSeekerAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.search -> replaceFragment(Search())
                R.id.wishlist -> replaceFragment(WishList())
                R.id.profile -> replaceFragment(Profile())

                else -> {

                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction().replace(R.id.nav_container , fragment).commit()

    }
}