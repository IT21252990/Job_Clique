package com.example.jobclique

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.firestore.FirebaseFirestore

class JobPostAdapter( private val context: Context, private val postList: ArrayList<JobPostsModel>):
    RecyclerView.Adapter<JobPostAdapter.MyViewHolder>(){

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    inner class MyViewHolder(itemView: View): ViewHolder(itemView){
        lateinit var jobId:TextView
       var jobName: TextView
       var jobDescrip : TextView
       var jobDate : TextView
       var jobCNumber : TextView
       var jobSRange: TextView
       var mMenus :ImageView
       init {
           jobName= itemView.findViewById<TextView>(R.id.jobNameget)
           jobDescrip= itemView.findViewById<TextView>(R.id.jobDescripget)
           jobDate = itemView.findViewById<TextView>(R.id.addedDateget)
           jobCNumber = itemView.findViewById<TextView>(R.id.jobCNoget)
           jobSRange = itemView.findViewById<TextView>(R.id.jobSRangeget)

           mMenus = itemView.findViewById(R.id.mMenus)

           mMenus.setOnClickListener{ popupMenus(it) }
       }

       private fun popupMenus(itemView: View) {
           val position = postList[adapterPosition]
           val popupMenus = PopupMenu(context,itemView)

           popupMenus.inflate(R.menu.show_menu)
           popupMenus.setOnMenuItemClickListener {
               when(it.itemId){
                   R.id.editText -> {

                       val itemView = LayoutInflater.from(context).inflate(R.layout.fragment_add_post, null)

                       AlertDialog.Builder(context)
                           .setView(itemView)
                           .setPositiveButton("Ok") { dialog, _ ->
                               val documentId = postList[adapterPosition]
                               val updatedJobName = jobName.text.toString()
                               val updatedJobDescrip = jobDescrip.text.toString()
                               val updatedJobDate = jobDate.text.toString()
                               val updatedJobCNumber = jobCNumber.text.toString()
                               val updatedJobSRange = jobSRange.text.toString()

                               val postRef =
                                   documentId?.let { it1 ->
                                       firestore.collection("JobPosts").document(
                                           it1.toString()
                                       )
                                   }
                               if (postRef != null) {
                                   postRef.update(
                                       "JobName", updatedJobName,
                                       "JobDescrip", updatedJobDescrip,
                                       "JobDate", updatedJobDate,
                                       "JobCNumber", updatedJobCNumber,
                                       "JobSalary", updatedJobSRange
                                   )
                                       .addOnSuccessListener {
                                           notifyDataSetChanged()
                                           Toast.makeText(context, "Job Post information is updated!", Toast.LENGTH_SHORT).show()
                                       }
                                       .addOnFailureListener { e ->
                                           Toast.makeText(context, "Failed to update job post: ${e.message}", Toast.LENGTH_SHORT).show()
                                       }
                                       .addOnCompleteListener { dialog.dismiss() }
                               }
                           }
                                  .setNegativeButton("Cancel"){
                                      dialog, _ ->
                                      dialog.dismiss()
                                  }
                                  .create()
                                  .show()
                       true
                   }
                   R.id.deleteText -> {

                       AlertDialog.Builder(context)
                           .setTitle("Delete")
                           .setIcon(R.drawable.ic_warning)
                           .setMessage("Are you sure you want to delete this Post?")
                           .setPositiveButton("Yes") { dialog, _ ->

                               val documentId = postList[adapterPosition]
                               if (documentId != null) {
                                   firestore.collection("JobPosts").document(documentId.toString())
                                       .delete()
                                       .addOnSuccessListener {
                                           postList.removeAt(adapterPosition)
                                           notifyDataSetChanged()
                                           Toast.makeText(context, "Post is deleted!", Toast.LENGTH_SHORT).show()
                                       }
                                       .addOnFailureListener { e ->
                                           Toast.makeText(context, "Failed to delete post: ${e.message}", Toast.LENGTH_SHORT).show()
                                       }
                                       .addOnCompleteListener { dialog.dismiss() }
                               }
                           }
                           .setNegativeButton("No"){
                               dialog, _ ->
                               dialog.dismiss()
                           }
                           .create()
                           .show()

                       true
                   }
                   else -> true
               }
           }
           popupMenus.show()
           val popup = PopupMenu::class.java.getDeclaredField("mPopup")
           popup.isAccessible = true
           val menu = popup.get(popupMenus)
           menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
               .invoke(menu,true)
       }
   }

    private lateinit var mListener: onItemClickListener

    interface OnDeleteClickListener {
        fun onDeleteClick(documentId: String)
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickLitener(clickListener: onItemClickListener){
        mListener = clickListener
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

        // Assuming you have a reference to the Firestore collection
        val collectionRef = firestore.collection("JobPosts")

        // Retrieve the documents in the collection
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val documentId = document.id
                    // Use the documentId as needed
                    // You can store it in a list, pass it to a function, etc.
//                    println("Document ID: $documentId")
                }
            }
            .addOnFailureListener { e ->
                // Handle any errors
                println("Failed to retrieve documents: ${e.message}")
            }
        val document = postList[position]

    }

    override fun getItemCount(): Int {
        return postList.size
    }


}