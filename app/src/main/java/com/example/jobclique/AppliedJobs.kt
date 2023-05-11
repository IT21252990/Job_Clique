package com.example.jobclique

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class AppliedJobs : Fragment() {

    var userID = FirebaseAuth.getInstance().currentUser!!.uid

    private val db = FirebaseFirestore.getInstance()
    private val jobApplicationCollection = db.collection("JobApplications").whereEqualTo("userID" , userID)

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : JobApplicationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_applied_jobs, container, false)

        //setContentView(R.layout.recyclerview_layout)

        recyclerView = view.findViewById(R.id.RecyclerAppliedJobsTable)

        recyclerView.layoutManager = LinearLayoutManager(context)

        //fetchData()
        jobApplicationCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val dataList = arrayListOf<JobApplicationData>()
                for (document in querySnapshot.documents) {
                    // Convert Firestore documents to instances of MyData
                    var documentId = document.id

                    val myData = JobApplicationData(
                        document.getString("id") ?:"",
                        document.getString("name") ?: "",
                        document.getString("email")?:"",
                        document.getString("phoneNo")?:"",
                        document.getString("email")?:"",
                        document.getString("status")?:"",
                        document.getTimestamp("appliedDate")?: Timestamp(0, 0)
                    )
                    myData.Id = documentId

                    dataList.add(myData)
                }
                // Notify the RecyclerView adapter that the data has changed
                adapter = JobApplicationAdapter(dataList)

                recyclerView.adapter = adapter

                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }


        return view
    }




}