package com.example.jobclique

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployerJobApplicationsAdapter(private val jobApplicationList: ArrayList<JobApplicationModel>)
    : RecyclerView.Adapter<EmployerJobApplicationsAdapter.ViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick( position: Int)
    }
    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner = clickListner
    }
    class ViewHolder(itemView: View, clickListner : onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val tvname: TextView = itemView.findViewById(R.id.tvApplicationName)
        val tvemail: TextView = itemView.findViewById(R.id.tvApplicationEmail)
        val tvstatus: TextView = itemView.findViewById(R.id.tvApplicationStatus)
        init{
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recieved_applications_item, parent, false)
        return ViewHolder(view, mListner)
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