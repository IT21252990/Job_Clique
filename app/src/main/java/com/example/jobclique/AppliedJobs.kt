package com.example.jobclique

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class AppliedJobs : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val jobApplicationCollection = db.collection("JobApplications")

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
                    val myData = JobApplicationData(  document.getString("id") ?:"", document.getString("Status") ?: "")
                    dataList.add(myData)
                }
                // Notify the RecyclerView adapter that the data has changed
                adapter = JobApplicationAdapter(dataList)
                //adapter.setOnItemDeleteListener()
                recyclerView.adapter = adapter

                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }


        return view
    }

//    fun fetchData(): List<JobApplicationData> {
//        val items = mutableListOf<JobApplicationData>()
//        val query = jobApplicationCollection.get()
//        val results = query.await()
//        for (doc in results) {
//            val appliedDate = doc.getDateTime("AppliedDate")?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime() ?: LocalDateTime.now()
//            val status = doc.getString("Status") ?: ""
//            val jobApplication = JobApplicationData(appliedDate, status)
//            items.add(jobApplication)
//        }
//        return items
//    }


}