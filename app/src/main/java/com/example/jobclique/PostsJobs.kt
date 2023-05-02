package com.example.jobclique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class PostsJobs : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_posts_jobs, container, false)
        val requestB : Button = view.findViewById(R.id.btnRequest)
        requestB.setOnClickListener{
            val fragment = addPost()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout_employer, fragment)?.commit()
        }
        return view
    }

    }

