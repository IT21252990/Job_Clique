package com.example.jobclique

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EmployerJobApplications : Fragment() {

    var userId = FirebaseAuth.getInstance().currentUser!!.uid

    private val db = FirebaseFirestore.getInstance()
    private val jobApplicationCollection = db.collection("JobApplications").whereEqualTo("employerID" , userId)

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : EmployerJobApplicationsAdapter

    private lateinit var documentId :String

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_employer_job_applications, container, false)

        val Button : Button = view.findViewById(R.id.applicants)
        Button.setOnClickListener{
            val fragment = EmployerJobApplicationsDetailed()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout_employer,fragment)?.commit()
        }

        recyclerView = view.findViewById(R.id.RecyVReceivedAppl)

        recyclerView.layoutManager = LinearLayoutManager(context)

        //fetchData()
        jobApplicationCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val dataList = arrayListOf<JobApplicationModel>()
                for (document in querySnapshot.documents) {

                    documentId = document.id

                    // Convert Firestore documents to instances of MyData
                    val myData = JobApplicationModel(
//                        document.getString("ApplicationID") ?: "",
                        document.getString("name") ?: "",
                        document.getString("email")?:"",
                        document.getString("phoneNo")?:"",
                        document.getString("optional")?:"",
                        document.getString("status")?:"",
                        document.getString("id")?:"",
                        //document.get("AppliedDate")?:""?.as FieldValue?,
                        //document.getString("UserID")?:"",


                    )
                    dataList.add(myData)
                }
                // Notify the RecyclerView adapter that the data has changed
                adapter = EmployerJobApplicationsAdapter(dataList)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListner(object:EmployerJobApplicationsAdapter.onItemClickListner{
                    override fun onItemClick(position: Int) {
                        //val intent = Intent(this,EmployerJobApplicationsDetailed::class.java)

                        val fragment = EmployerJobApplicationsDetailed()

                        //put extras
                        //fragment.putExtra("ApplicationID" , dataList[position].ApplicationID)
                        val bundle = Bundle().apply {
                            //putString("ApplicationID", dataList[position].ApplicationID)
                            putString("name", dataList[position].Name)
                            putString("email", dataList[position].Email)
                            putString("phoneNo", dataList[position].PhoneNo)
                            putString("optional", dataList[position].Optional)
                            putString("status", dataList[position].Status)
                            putString("id", documentId )
                        }
                        fragment.arguments = bundle

                        val transaction = fragmentManager?.beginTransaction()
                        transaction?.replace(R.id.frame_layout_employer,fragment)?.commit()


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