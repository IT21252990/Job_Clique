package com.example.jobclique

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class resetPassword : Fragment() {

    private lateinit var textEmail:EditText
    private lateinit var btnRestPassword: Button

    private lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_reset_password, container, false)

        textEmail = view.findViewById(R.id.resetPWEmail)
        btnRestPassword = view.findViewById(R.id.BtnRestPW)

        auth = FirebaseAuth.getInstance()

        btnRestPassword.setOnClickListener{
            val sEmail = textEmail.text.toString()
            auth.sendPasswordResetEmail(sEmail).addOnSuccessListener {
                Toast.makeText(context,"Please check your Email" , Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context,it.toString() , Toast.LENGTH_SHORT).show()

            }
        }
        // Inflate the layout for this fragment
        return view
    }
}