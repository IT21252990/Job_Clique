package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class resetPassword : AppCompatActivity() {

    private lateinit var textEmail:EditText
    private lateinit var btnRestPassword: Button

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        textEmail = findViewById(R.id.resetPWEmail)
        btnRestPassword = findViewById(R.id.BtnRestPW)

        auth = FirebaseAuth.getInstance()

        btnRestPassword.setOnClickListener{
            val sEmail = textEmail.text.toString()
            auth.sendPasswordResetEmail(sEmail).addOnSuccessListener {
                Toast.makeText(this,"Please check your Email" , Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,it.toString() , Toast.LENGTH_SHORT).show()

            }
        }
        // Inflate the layout for this fragment
    }
}