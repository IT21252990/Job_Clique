package com.example.jobclique

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


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
        val finalPosition = position

        val JobApplicationData = jobApplicationList[finalPosition]
        //holder.appliedDate.text = JobApplicationData.AppliedDate.toString()
   //     holder.status.text = JobApplicationData.Status

//        holder.deletebtn.setOnClickListener {
//            onItemDeleteListener?.onItemDelete(position)
//        }

        holder.status.text = JobApplicationData.status

        holder.deletebtn.setOnClickListener {
            val builder = AlertDialog.Builder(holder.status.context)
            builder.setTitle("Are you Sure ?")
            builder.setMessage("Deleted data can't be Undo")
            builder.setPositiveButton("Delete") { dialog, which ->
                FirebaseFirestore.getInstance().collection("JobApplications").get()
                    .onSuccessTask(){
                        FirebaseFirestore.getInstance().collection("JobApplications").document("UserID").delete()
                    }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(holder.status.context, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            builder.show()
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

