package com.example.jobclique

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LatestJobsAdapter(private val jobsList : ArrayList<JobPosts>) : RecyclerView.Adapter<LatestJobsAdapter.MyViewHolder>() {

    var onItemClick : ((JobPosts) -> Unit )? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestJobsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.latest_jobs_item ,
        parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LatestJobsAdapter.MyViewHolder, position: Int) {

        val jobPosts : JobPosts = jobsList[position]
        holder.jobTitle.text = jobPosts.Title
//        holder.employerID.text = jobPosts.EmployerID.toString()
        holder.salary.text = jobPosts.SalaryRange

        holder.applybtn.setOnClickListener(){
            onItemClick?.invoke(jobPosts)
        }

    }

    override fun getItemCount(): Int {
        return jobsList.size
    }

    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val jobTitle : TextView = itemView.findViewById(R.id.title)
//        val employerID : TextView = itemView.findViewById(R.id.EmployerName)
        val salary : TextView = itemView.findViewById(R.id.Salary)
        val applybtn : Button = itemView.findViewById(R.id.btnApplyJob)

    }
}