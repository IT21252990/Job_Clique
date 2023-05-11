package com.example.jobclique

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LatestJobsAdapter(private val jobsList : ArrayList<JobPosts>) : RecyclerView.Adapter<LatestJobsAdapter.MyViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick( position: Int)
    }
    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner = clickListner
    }

    //var onItemClick : ((JobPosts) -> Unit )? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestJobsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.latest_jobs_item ,
        parent, false)

        return MyViewHolder(itemView , mListner)
    }
    override fun onBindViewHolder(holder: LatestJobsAdapter.MyViewHolder, position: Int) {
        val jobPosts : JobPosts = jobsList[position]
        holder.jobTitle.text = jobPosts.jobName

        //holder.employerID.text = jobPosts.employerID

        val documentId =  jobPosts.employerID

        if (documentId != null) {
            FirebaseFirestore.getInstance().collection("Employers")
                .document(documentId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val empName = documentSnapshot.getString("CompanyName")
                        holder.employerID.text = empName
                    } else {
                        holder.employerID.text = "Employer Name"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }


        holder.salary.text = jobPosts.jobSalary
//        holder.applybtn.setOnClickListener(){
//            onItemClick?.invoke(jobPosts)
//        }
        holder.btnWishList.setOnCheckedChangeListener { buttonView, isChecked ->

            val db = FirebaseFirestore.getInstance()
            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            val data = mapOf(
                "UserID" to userID
            )
            if (isChecked) {
                db.collection("Wishlist").add(data)
            }
            if (!isChecked) {

            }


        }

    }
    override fun getItemCount(): Int {
        return jobsList.size
    }
    public class MyViewHolder(itemView : View , clickListner : onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val jobTitle : TextView = itemView.findViewById(R.id.title)
        val employerID : TextView = itemView.findViewById(R.id.EmployerName)
        val salary : TextView = itemView.findViewById(R.id.Salary)
        val applybtn : Button = itemView.findViewById(R.id.btnApplyJob)

        val btnWishList : CheckBox = itemView.findViewById(R.id.btnAddWishlist)


        init{
            applybtn.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}