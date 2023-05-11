package com.example.jobclique
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class Home : Fragment() {

    private lateinit var fStore: FirebaseFirestore

    private lateinit var recyclerView: RecyclerView
    private lateinit var jobPostsArrayList: ArrayList<JobPosts>
    private lateinit var latestJobsAdapter: LatestJobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


    recyclerView = view.findViewById(R.id.RecyclerLatestJobs)
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.setHasFixedSize(true)

    jobPostsArrayList = arrayListOf()
    latestJobsAdapter = LatestJobsAdapter(jobPostsArrayList)
    recyclerView.adapter = latestJobsAdapter

    latestJobsAdapter.onItemClick={
        val intent = Intent(requireContext(), JobApplication::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    EventChangeListner()
    return view }
    private fun EventChangeListner() {
        fStore = FirebaseFirestore.getInstance()
        fStore.collection("JobPosts").orderBy("jobDate", Query.Direction.ASCENDING).
                addSnapshotListener(object : EventListener<QuerySnapshot>{
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
                                jobPostsArrayList.add(dc.document.toObject(JobPosts::class.java))
                            }
                        }
                        latestJobsAdapter.notifyDataSetChanged()
                    }

                })
    }
}