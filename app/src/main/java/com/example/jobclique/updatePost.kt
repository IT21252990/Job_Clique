package com.example.jobclique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class updatePost : AppCompatActivity() {

    private lateinit var JobName: EditText
    private lateinit var JobDescrip: EditText
    private lateinit var JobDate: EditText
    private lateinit var JobSalary: EditText
    private lateinit var JobCNumber: EditText
    private lateinit var btnUpdatePost: Button

    private lateinit var progressBar: ProgressBar

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_post)

        JobName = findViewById(R.id.jobNameupdate)
        JobDescrip = findViewById(R.id.jobDescripupdate)
        JobDate = findViewById(R.id.addedDateupdate)
        JobSalary = findViewById(R.id.jobSRangeupdate)
        JobCNumber = findViewById(R.id.jobCNumberupdate)

        setData()

        btnUpdatePost.setOnClickListener {
            val jname = JobName.text.toString()
            val jDescrip = JobDescrip.text.toString()
            val jDate = JobDate.text.toString()
            val jSalary = JobSalary.text.toString()
            val jCNumber = JobCNumber.text.toString()

            val updateMap = mapOf(
                "JobName" to jname,
                "JobDescrip" to jDescrip,
                "JobDate" to jDate,
                "JobCNumber" to jCNumber,
                "JobSalary" to jSalary
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("JobPosts").document().update(updateMap)

            Toast.makeText(this, "Successfully Updated!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setData(){
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("JobPosts").document(userId)

        ref.get().addOnSuccessListener {
            if (it != null){
                val JobName = it.data?.get("JobName")?.toString()
                val JobDescrip = it.data?.get("JobDescrip")?.toString()
                val JobDate = it.data?.get("JobDate")?.toString()
                val JobSalary = it.data?.get("JobSalary")?.toString()
                val JobCNumber = it.data?.get("JobCNumber")?.toString()
            }
        }

            .addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
    }
}

