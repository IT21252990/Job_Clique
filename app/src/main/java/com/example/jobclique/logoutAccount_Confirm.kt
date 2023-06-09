package com.example.jobclique

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth


class logoutAccount_Confirm : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logout_account_confirm, container, false)

        val logout: Button = view.findViewById(R.id.btnAccountDelete)
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), userLoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Inflate the layout for this fragment
        return view
    }
}
