package com.example.jobclique

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.textfield.TextInputEditText

class JobPostAdapter (private val postList: ArrayList<JobPostsModel>):
    RecyclerView.Adapter<JobPostAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): ViewHolder(itemView){
        val jobName: AppCompatTextView = itemView.findViewById(R.id.jobNameget)
        val jobDescrip:AppCompatTextView = itemView.findViewById(R.id.jobDescripget)
        val jobDate:AppCompatTextView = itemView.findViewById(R.id.addedDateget)
//       val jobDateText: String = itemView.JobDate.toDate().toString()
        val jobCNumber:AppCompatTextView = itemView.findViewById(R.id.jobCNoget)
        val jobSRange:AppCompatTextView = itemView.findViewById(R.id.jobSRangeget)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(documentId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView=
           LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.run {
            jobName.setText(currentPost.JobName)
            jobDescrip.setText(currentPost.JobDescrip)
            jobDate.setText(currentPost.JobDate)
            jobCNumber.setText(currentPost.JobCNumber)
            jobSRange.setText(currentPost.JobSalary)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val deleteButton: Button = itemView.findViewById(R.id.deletePostBtn)
        }

        val document = postList[position]
        val documentId = document.id

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}