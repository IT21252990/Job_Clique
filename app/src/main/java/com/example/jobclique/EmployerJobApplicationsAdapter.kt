package com.example.jobclique

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployerJobApplicationsAdapter(private val jobApplicationList: ArrayList<JobApplicationModel>) : RecyclerView.Adapter<EmployerJobApplicationsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvname: TextView = itemView.findViewById(R.id.tvApplicationName)
        val tvemail: TextView = itemView.findViewById(R.id.tvApplicationEmail)
        val tvstatus: TextView = itemView.findViewById(R.id.tvApplicationStatus)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recieved_applications_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val finalPosition = position

        val JobApplicationModel = jobApplicationList[finalPosition]
        holder.tvname.text = JobApplicationModel.Name
        holder.tvemail.text = JobApplicationModel.Email
        holder.tvstatus.text = JobApplicationModel.Status


    }

    override fun getItemCount() = jobApplicationList.size


}