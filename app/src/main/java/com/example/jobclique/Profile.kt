package com.example.jobclique
import android.annotation.SuppressLint
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

class Profile : Fragment() {

    private lateinit var fullName: EditText
    private lateinit var email : EditText
    private lateinit var btnUpdateJobSeekerProfile : Button

    private var db = FirebaseFirestore.getInstance()
    private lateinit var userID: String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        userID = FirebaseAuth.getInstance().currentUser!!.uid

        fullName = view.findViewById(R.id.jobSeekerFullName)
        email = view.findViewById(R.id.employerEmail)
        btnUpdateJobSeekerProfile = view.findViewById(R.id.btnEmployerProfileUpdate)

        setFireStoreData()

        btnUpdateJobSeekerProfile.setOnClickListener{

            val updatedFullName = fullName.text.toString().trim()
            val updatedEmail = email.text.toString().trim()

            val mapUpdate = mapOf(
                "FullName" to updatedFullName,
                "UserEmail" to updatedEmail
            )

            db.collection("Users").document(userID).update(mapUpdate).addOnSuccessListener {
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

        val dltAccount : Button = view.findViewById(R.id.deleteMyAccount)
        dltAccount.setOnClickListener{
            val fragment = deleteAccount_Confirm()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container,fragment)?.commit()
        }

        val appliedJobs : Button = view.findViewById(R.id.appliedJobs)
        appliedJobs.setOnClickListener{
            val fragment = AppliedJobs()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container,fragment)?.commit()
        }

        val myWistList : Button = view.findViewById(R.id.btnMyWishlist)
        myWistList.setOnClickListener{
            val fragment = WishList()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container,fragment)?.commit()
        }

        val logout : Button = view.findViewById(R.id.btnLogout)
        logout.setOnClickListener{
            val fragment = logoutAccount_Confirm()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container,fragment)?.commit()
        }


        return view
    }

    private fun setFireStoreData(){
        val ref = db.collection("Users").document(userID)
        ref.get().addOnSuccessListener {
            if(it != null){
                val fullname = it.data?.get("FullName")?.toString()
                val Email = it.data?.get("UserEmail")?.toString()

                fullName.setText(fullname)
                email.setText(Email)
            }
        }.addOnFailureListener{
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }
}




















