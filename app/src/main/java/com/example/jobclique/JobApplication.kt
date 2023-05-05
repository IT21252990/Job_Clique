package com.example.jobclique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class JobApplication : AppCompatActivity() {

    private lateinit var etname: EditText
    private lateinit var etemail: EditText
    private lateinit var etphone: EditText
    private lateinit var etoptional: EditText
    private lateinit var btnSend: Button

//  private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef: CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_application)

        etname = findViewById(R.id.edtTextName)
        etemail = findViewById(R.id.editTextEmail)
        etphone = findViewById(R.id.editTextPhone)
        etoptional = findViewById(R.id.edtTextOptional)
        btnSend = findViewById(R.id.btnApplicationSend)

//        dbRef = FirebaseDatabase.getInstance().getReference("Applications")
        dbRef = FirebaseFirestore.getInstance().collection("JobApplications")

        btnSend.setOnClickListener{
            sendApplication()
        }

    }

    private fun sendApplication(){

        //getting values
        val name = etname.text.toString()
        val email = etemail.text.toString()
        val phone = etphone.text.toString()
        val optional = etoptional.text.toString()

        if ( name.isEmpty() ){
            etname.error = "Please enter the name !"
        }
        if ( email.isEmpty() ){
            etemail.error = "Email required !"
        }
        if ( phone.isEmpty() ){
            etphone.error = "Phone number required !"
        }

//        val applicationId = dbRef.push().key!!
//
//        val application = JobApplicationModel(applicationId,name,email,phone)
//
//        dbRef.child(applicationId).setValue(application)
//            .addOnCompleteListener{
//                Toast.makeText(this,"Application sent successfully !", Toast.LENGTH_LONG).show()
//
//                etname.text.clear()
//                etemail.text.clear()
//                etphone.text.clear()
//                etoptional.text.clear()
//
//            }
//            .addOnFailureListener{ err ->
//                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
//            }

        val application = JobApplicationModel(name, email, phone,optional)

        dbRef.add(application)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Application sent successfully!", Toast.LENGTH_LONG).show()

                etname.text.clear()
                etemail.text.clear()
                etphone.text.clear()
                etoptional.text.clear()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error ${exception.message}", Toast.LENGTH_LONG).show()
            }


    }
}