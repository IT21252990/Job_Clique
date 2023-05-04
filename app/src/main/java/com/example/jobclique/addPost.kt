package com.example.jobclique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class addPost : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        val addB : Button = view.findViewById(R.id.addPostBtn)

        addB.setOnClickListener{
            val fragment = PostsJobs()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout_employer , fragment)?.commit()
        }
        return view
    }

}