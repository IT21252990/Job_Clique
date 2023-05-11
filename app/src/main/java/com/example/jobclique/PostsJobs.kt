package com.example.jobclique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostsJobs : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postList: ArrayList<JobPostsModel>
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val view = inflater.inflate(R.layout.fragment_posts_jobs, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_postjobs)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        postList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        db.collection("JobPosts").get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty){
                    for (document in querySnapshot.documents){
                        val post: JobPostsModel? = document.toObject(JobPostsModel::class.java)
                        if (post != null){
                           postList.add(post)
                        }
                    }

                    recyclerView.adapter = context?.let { JobPostAdapter(it,postList) }
                }
            }
            .addOnFailureListener { exception->
//                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT.show())
                Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_SHORT).show()
            }
        return view

    }
//
//    override fun onDeleteClick(documentId: String) {
//        val db = Firebase.firestore
//        val collectionRef = db.collection("JobPosts")
//
//        collectionRef.document(documentId)
//            .delete()
//            .addOnSuccessListener {
//                // Document deleted successfully
//            }
//            .addOnFailureListener { e ->
//                // Handle any errors
//            }
//    }

}

