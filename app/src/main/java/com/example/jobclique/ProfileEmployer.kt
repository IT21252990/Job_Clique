package com.example.jobclique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ProfileEmployer : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_employer, container, false)
        val resetButton : Button = view.findViewById(R.id.btnResetPwemp)
        resetButton.setOnClickListener{
            val fragment = resetPassword()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container_emp,fragment)?.commit()
        }
        return view
    }

}