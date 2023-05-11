package com.example.jobclique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class addPost : Fragment() {

    private lateinit var currentDocumentRef: DocumentReference


    private lateinit var jobName: TextInputEditText
    private lateinit var jobDescrip: TextInputEditText
    private lateinit var jobDate: TextInputEditText
    private lateinit var jobCNumber: TextInputEditText
    private lateinit var jobSRange: TextInputEditText

    private lateinit var btnaddPost: Button

    private lateinit var dbRef: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_post, container, false)

        jobName = rootView.findViewById(R.id.jobNameField)
        jobDescrip = rootView.findViewById(R.id.jobDescripField)
        jobDate = rootView.findViewById(R.id.addedDateField)
        jobSRange = rootView.findViewById(R.id.salaryRangeField)
        jobCNumber = rootView.findViewById(R.id.phoneField)

        btnaddPost = rootView.findViewById(R.id.addPostBtn)

        btnaddPost.setOnClickListener { saveJobPostsData() }

        dbRef = FirebaseFirestore.getInstance()

//        dbRef.collection("Employers").document()

        dbRef = FirebaseFirestore.getInstance()
        currentDocumentRef = dbRef.collection("JobPosts").document()

        return rootView
    }

    private fun saveJobPostsData() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        val jobNameText = jobName.text.toString()
        val jobDescripText = jobDescrip.text.toString()
        val jobDateText = jobDate.text.toString()
        val jobCNumberText = jobCNumber.text.toString()
        val jobSRangeText = jobSRange.text.toString()

        if (jobNameText.isEmpty()){
            jobName.error = "Please enter name"
            return
        }
        if (jobDescripText.isEmpty()){
            jobDescrip.error = "Please enter description"
            return
        }
        if (jobDateText.isEmpty()){
            jobDate.error = "Please enter Date"
            return
        }
        if (jobCNumberText.isEmpty()){
            jobCNumber.error = "Please enter Contact Number"
            return
        }
        if (jobSRangeText.isEmpty()){
            jobSRange.error = "Please enter Salary Range"
            return
        }

        val currentDocumentId = currentDocumentRef.id

        val jPostID = dbRef.collection("JobPosts").document().id
        val jPosts = JobPostsModel(jobNameText, jobDescripText, jobDateText, jobCNumberText, jobSRangeText)
        dbRef.collection("JobPosts").document(currentDocumentId).set(jPosts)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Data inserted successfully", Toast.LENGTH_LONG).show()
                jobName.text?.clear()
                jobDescrip.text?.clear()
                jobDate.text?.clear()
                jobCNumber.text?.clear()
                jobSRange.text?.clear()

            }.addOnFailureListener{ e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
