package com.example.jobclique

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileEmployer : Fragment() {

    private lateinit var companyName: EditText
    private lateinit var email : EditText
    private lateinit var phone : EditText
    private lateinit var btnUpdateEmployerProfile : Button

    private var db = FirebaseFirestore.getInstance()
    private lateinit var userID: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_employer, container, false)

        userID = FirebaseAuth.getInstance().currentUser!!.uid

        companyName = view.findViewById(R.id.employerCompanyName)
        email = view.findViewById(R.id.employerEmail)
        phone = view.findViewById(R.id.employerPhone)
        btnUpdateEmployerProfile = view.findViewById(R.id.btnEmployerProfileUpdate)

        setFireStoreData()

        btnUpdateEmployerProfile.setOnClickListener{

            val updatedCompanyName = companyName.text.toString().trim()
            val updatedEmail = email.text.toString().trim()
            val updatedPhone = phone.text.toString().trim()

            val mapUpdate = mapOf(
                "CompanyName" to updatedCompanyName,
                "UserEmail" to updatedEmail,
                "UserPhone" to updatedPhone
            )

            db.collection("Employers").document(userID).update(mapUpdate).addOnSuccessListener {
                Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        val resetButton : Button = view.findViewById(R.id.btnResetPwemp)
        resetButton.setOnClickListener{
            val intent = Intent(requireContext(), resetPassword::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        val dltAccountemp : Button = view.findViewById(R.id.deleteMyAccountemp)
        dltAccountemp.setOnClickListener{
            val fragment = deleteAccount_Confirm()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout_employer,fragment)?.commit()
        }

        val logout : Button = view.findViewById(R.id.btnLogout)
        logout.setOnClickListener{
            val fragment = logoutAccount_Confirm()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout_employer,fragment)?.commit()
        }

        return view
    }

    private fun setFireStoreData(){
        val ref = db.collection("Employers").document(userID)
        ref.get().addOnSuccessListener {
            if(it != null){
                val sCompanyName = it.data?.get("CompanyName")?.toString()
                val sEmail = it.data?.get("UserEmail")?.toString()
                val sPhone = it.data?.get("UserPhone")?.toString()

                companyName.setText(sCompanyName)
                email.setText(sEmail)
                phone.setText(sPhone)
            }
        }.addOnFailureListener{
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

}