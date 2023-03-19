package com.example.jobclique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class EmployerJobApplications : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_employer_job_applications, container, false)
        val seeButton : Button = view.findViewById(R.id.btnSeeApplications)
        seeButton.setOnClickListener{
            val fragment = EmployerJobApplicationsDetailed()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout_employer,fragment)?.commit()
        }
        return view

    }

}