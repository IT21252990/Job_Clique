package com.example.jobclique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class JobApplication : AppCompatActivity() {

    private lateinit var employerID : String
    private lateinit var empName: TextView
    private lateinit var jobName: TextView


    private lateinit var etname: EditText
    private lateinit var etemail: EditText
    private lateinit var etphone: EditText
    private lateinit var etoptional: EditText
    private lateinit var btnSend: Button
    private var valid = true
    private lateinit var userID: String

    private lateinit var dbRef: CollectionReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_application)

        empName = findViewById(R.id.tvJobTitle)
        jobName = findViewById(R.id.tvJobSubHead)

        setValuesToViews()

        userID = FirebaseAuth.getInstance().currentUser!!.uid

        etname = findViewById(R.id.edtTextName)
        etemail = findViewById(R.id.editTextEmail)
        etphone = findViewById(R.id.editTextPhone)
        etoptional = findViewById(R.id.edtTextOptional)
        btnSend = findViewById(R.id.btnApplicationSend)

        dbRef = FirebaseFirestore.getInstance().collection("JobApplications")

        btnSend.setOnClickListener{

            checkField(etname)
            checkField(etemail)
            checkField(etphone)

            //getting values
            val name = etname.text.toString()
            val email = etemail.text.toString()
            val phone = etphone.text.toString()
            val optional = etoptional.text.toString()
            val status = "Pending"
            val appliedDate = FieldValue.serverTimestamp()
//            val user = userID

            if ( name.isEmpty() ){
                etname.error = "Please enter the name !"
                etname.requestFocus()
                return@setOnClickListener

            }
            if ( email.isEmpty() ){
                etemail.error = "Email required !"
                etemail.requestFocus()
                return@setOnClickListener
            }
            if( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etemail.error = ("Please Enter valid Email !")
                etemail.requestFocus()
                return@setOnClickListener
            }
            if ( phone.isEmpty() ){
                etphone.error = "Phone number required !"
                etphone.requestFocus()
                return@setOnClickListener
            }
            if( phone.length != 10){
                etphone.error = ("Phone number should have 10 digits !")
                etphone.requestFocus()
                return@setOnClickListener
            }

//            val application = JobApplicationModel(name, email, phone,optional,status,appliedDate,userID)

            val mapUpdate = mapOf(
                "name" to name,
                "email" to email,
                "phoneNo" to phone,
                "optinal" to optional,
                "status" to status,
                "appliedDate" to appliedDate,
                "userID" to userID,
                "employerID" to employerID
            )
            if (valid) {
                dbRef.add(mapUpdate)
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

    }


    private fun checkField(textField: EditText): Boolean {
        if (textField.text.toString().isEmpty()) {
            textField.error = "Error"
            valid = false
        } else {
            valid = true
        }

        return valid
    }


    private fun setValuesToViews(){
        val extras = intent.extras
        if (extras != null) {

            employerID = extras.getString("employerID").toString()

            if (employerID != null) {
                FirebaseFirestore.getInstance().collection("Employers")
                    .document(employerID)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            val name = documentSnapshot.getString("CompanyName")
                            empName.text = name
                        } else {
                            empName.text = "Employer Name"
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("TAG", "Error getting documents.", exception)
                    }
            }

            jobName.text = extras.getString("jobName").toString()



        }

    }

}