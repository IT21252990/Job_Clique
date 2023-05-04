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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var jobPostsArrayList: ArrayList<JobPosts>
    private lateinit var latestJobsAdapter: LatestJobsAdapter
    private lateinit var fStore: FirebaseFirestore
//
//
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
//
//        val applyJob : Button = view.findViewById(R.id.btnApplyJob)
//        applyJob.setOnClickListener{
//            val intent = Intent(requireContext(), JobApplication::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//        }

    recyclerView = view.findViewById(R.id.RecyclerLatestJobs)
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.setHasFixedSize(true)

    jobPostsArrayList = arrayListOf()

    latestJobsAdapter = LatestJobsAdapter(jobPostsArrayList)

    recyclerView.adapter = latestJobsAdapter

    EventChangeListner()


    return view }

    private fun EventChangeListner() {
        fStore = FirebaseFirestore.getInstance()
        fStore.collection("JobPosts").orderBy("AddedDate", Query.Direction.ASCENDING).
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