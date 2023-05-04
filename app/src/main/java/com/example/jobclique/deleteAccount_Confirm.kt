package com.example.jobclique

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class deleteAccount_Confirm : Fragment() {

    private lateinit var btnDeleteAccount : Button
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_delete_account__confirm, container, false)

        btnDeleteAccount = view.findViewById(R.id.btnAccountDelete)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        btnDeleteAccount.setOnClickListener{
            val user = Firebase.auth.currentUser
            user?.delete()?.addOnCompleteListener() {
                if(it.isSuccessful){
                    //account has been deleted
                    Toast.makeText(context,"Account Successfully Deleted ! ", Toast.LENGTH_SHORT).show()
                    //since account is already been deleted we cannot sign out
                    //so we just start an activity
                    val intent = Intent(requireContext(),userLoginActivity::class.java)
                    startActivity(intent)
                    //destroy this activity
                    requireActivity().finish()
                }else{
                    //catch error
                    Log.e("error : " , it.exception.toString())
                }
            }
        }

        // Inflate the layout for this fragment
        return view
    }



}

