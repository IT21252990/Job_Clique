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

    private lateinit var fStore: FirebaseFirestore

    private lateinit var recyclerView: RecyclerView
    private lateinit var jobApplicationArrayList : ArrayList<JobApplicationData>
    private lateinit var jobApplicationAdapter: JobApplicationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_applied_jobs, container, false)

        recyclerView = view.findViewById(R.id.RecyclerAppliedJobsTable)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        jobApplicationArrayList = arrayListOf()
        jobApplicationAdapter = JobApplicationAdapter(jobApplicationArrayList)
        recyclerView.adapter = jobApplicationAdapter

//delete
//        jobApplicationAdapter.setOnItemClickListener(object : JobApplicationAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                jobApplicationArrayList.removeAt(position)
//                jobApplicationAdapter.notifyItemRemoved(position)
//            }
//        })
//delete


        EventChangeListner()



        return view
    }

    private fun EventChangeListner() {
        fStore = FirebaseFirestore.getInstance()
        fStore.collection("JobApplications").orderBy("AppliedDate", Query.Direction.ASCENDING).
        addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if( error != null ){
                    Log.e("FireStore Error" , error.message.toString())
                    return
                }

                for ( dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        jobApplicationArrayList.add(dc.document.toObject(JobApplicationData::class.java))
                        val documentId: String = dc.document.id

                    }
                }

                jobApplicationAdapter.notifyDataSetChanged()
            }

        })
    }


}