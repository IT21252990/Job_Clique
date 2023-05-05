package com.example.jobclique

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

//class JobApplicationAdapter(private val jobApplicationList : ArrayList<JobApplicationData>)
//    : RecyclerView.Adapter<JobApplicationAdapter.MyViewHolder> () {
//
//    //delete
//    private lateinit var listner: OnItemClickListener
//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnItemClickListener(clickListener: OnItemClickListener) {
//        listner = clickListener
//    }
//    //delete
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobApplicationAdapter.MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.applied_jobs_table_row ,
//            parent, false)
//
//        return JobApplicationAdapter.MyViewHolder(itemView , listner)
//    }
//
//    override fun onBindViewHolder(holder: JobApplicationAdapter.MyViewHolder, position: Int) {
//
//        val jobApplicationData: JobApplicationData = jobApplicationList[position]
////        holder.jobPostTitle.text =
////        holder.jobPostEmployer.text =
//        holder.appliedDate.text = jobApplicationData.AppliedDate?.toDate().toString()
//        holder.status.text = jobApplicationData.Status
//
//    }
//
//    override fun getItemCount(): Int {
//        return jobApplicationList.size
//    }
//
//    public class MyViewHolder(itemView: View ,listner: OnItemClickListener ) : RecyclerView.ViewHolder(itemView){
//
//        val jobPostTitle : TextView = itemView.findViewById(R.id.tvjobTitle)
//        val jobPostEmployer : TextView = itemView.findViewById(R.id.tvEmployer)
//        val appliedDate : TextView = itemView.findViewById(R.id.tvAppliedDate)
//        val status : TextView = itemView.findViewById(R.id.tvStatus)
//        private var deletebtn : ImageButton = itemView.findViewById(R.id.btnApplicationDelete)
//        init {
//            deletebtn.setOnClickListener(){
//                listner?.onItemClick(adapterPosition)
//            }
//        }
//
//
//    }
//}

interface OnItemDeleteListener {
    fun onItemDelete(position: Int)
}

class JobApplicationAdapter(private val jobApplicationList: ArrayList<JobApplicationData>) : RecyclerView.Adapter<JobApplicationAdapter.ViewHolder>(), OnItemDeleteListener{



    private var onItemDeleteListener: OnItemDeleteListener? = null

    fun setOnItemDeleteListener(listener: OnItemDeleteListener) {
        onItemDeleteListener = listener
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appliedDate: TextView = itemView.findViewById(R.id.tvAppliedDate)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val deletebtn : ImageButton = itemView.findViewById(R.id.btnApplicationDelete)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.applied_jobs_table_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val JobApplicationData = jobApplicationList[position]
        //holder.appliedDate.text = JobApplicationData.AppliedDate.toString()
        holder.status.text = JobApplicationData.Status

        holder.deletebtn.setOnClickListener {
            onItemDeleteListener?.onItemDelete(position)
        }


    }

    override fun getItemCount() = jobApplicationList.size

    override fun onItemDelete(position: Int) {
        val jobApplicationData = jobApplicationList[position]
        jobApplicationList.removeAt(position)
        notifyItemRemoved(position)
        deleteJobApplicationFromFirestore(jobApplicationData)
    }

    private fun deleteJobApplicationFromFirestore(jobApplicationData: JobApplicationData) {
        FirebaseFirestore.getInstance().collection("JobApplications")
            .document(jobApplicationData.Id)
            .delete()
            .addOnSuccessListener {
                Log.d("TAG", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error deleting document", e)
            }
    }
}

