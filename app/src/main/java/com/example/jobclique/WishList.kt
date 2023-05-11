package com.example.jobclique
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class WishList : Fragment() {

    private lateinit var fStore: FirebaseFirestore

    private val db = FirebaseFirestore.getInstance()
    private val jobPostsCollection = db.collection("Wishlist")
    private lateinit var adapter: LatestJobsAdapter

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_wish_list, container, false)

        recyclerView = view.findViewById(R.id.recycler_wishlist)
        recyclerView.layoutManager = LinearLayoutManager(context)

//        latestJobsAdapter.onItemClick={
//            val intent = Intent(requireContext(), JobApplication::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//        }
        //fetchData()
        jobPostsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val dataList = arrayListOf<JobPosts>()
                for (document in querySnapshot.documents) {

                    // Convert Firestore documents to instances of MyData
                    val myData = JobPosts(

                        document.getString("jobName") ?: "",
                        document.getString("jobSalary")?:"",
                        document.getString("employerID")?:"",

                        )


                    dataList.add(myData)
                }
                // Notify the RecyclerView adapter that the data has changed
                adapter = LatestJobsAdapter(dataList)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListner(object:LatestJobsAdapter.onItemClickListner{
                    override fun onItemClick(position: Int) {

                        val intent = Intent(requireContext(), JobApplication::class.java)


                        //put extras

                        val bundle = Bundle().apply {
                            //putString("ApplicationID", dataList[position].ApplicationID)
                            putString("employerID", dataList[position].employerID)
                            putString("jobName", dataList[position].jobName)
                        }

                        intent.putExtras(bundle)

                        startActivity(intent)
                        requireActivity().finish()

                    }

                })

                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }


        return view
    }
}


