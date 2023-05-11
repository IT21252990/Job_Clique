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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


interface OnItemDeleteListener {
    fun onItemDelete(position: Int)
}

class JobApplicationAdapter(private val jobApplicationList: ArrayList<JobApplicationData>) : RecyclerView.Adapter<JobApplicationAdapter.ViewHolder>(), OnItemDeleteListener{


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val applicant: TextView = itemView.findViewById(R.id.tvApplicant)
        val company: TextView = itemView.findViewById(R.id.tvCompany)
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

        holder.applicant.text = JobApplicationData.name

        //company name
        val documentId =  JobApplicationData.employerID

        if (documentId != null) {
            FirebaseFirestore.getInstance().collection("Employers")
                .document(documentId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val empName = documentSnapshot.getString("CompanyName")
                        holder.company.text = empName
                    } else {
                        holder.company.text = "Employer Name"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }

        //holder.company.text = JobApplicationData.employerID

        //applied date
        val appliedDate: Timestamp? = JobApplicationData.appliedDate
        if (appliedDate != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formattedDate: String = dateFormat.format(appliedDate.toDate())

            holder.appliedDate.text = formattedDate

        } else {
            holder.appliedDate.text = "No date available"
        }

        holder.status.text = JobApplicationData.status

        holder.deletebtn.setOnClickListener {
            val builder = AlertDialog.Builder(holder.status.context)
            builder.setTitle("Do you wanted to delete the application ?")
            builder.setMessage("Deleted data can't be Undo!")
            builder.setPositiveButton("Delete") { dialog, which ->
                onItemDelete(position)
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
                Log.d("TAG", "Successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error deleting document", e)
            }
    }
}

