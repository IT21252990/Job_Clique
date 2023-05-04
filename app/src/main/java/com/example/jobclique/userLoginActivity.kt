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

class userLoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var btngotoSignUp: TextView
    private lateinit var forgotPassword: TextView
    private var valid = true
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginEmail)
        password = findViewById(R.id.loginPassword)
        loginBtn = findViewById(R.id.loginBtn)
        btngotoSignUp = findViewById(R.id.btngotoSignUp)
        forgotPassword = findViewById(R.id.forgotPWlink)

        loginBtn.setOnClickListener {
            checkField(email)
            checkField(password)

            if (valid) {
                // Start the user login process
                fAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnSuccessListener { authResult: AuthResult ->
                        Toast.makeText(this@userLoginActivity, "Login is Successfully", Toast.LENGTH_SHORT).show()
                        checkUserAccessLevel(authResult.user!!.uid)
                    }
                    .addOnFailureListener { e: Exception ->
                        Toast.makeText(this@userLoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }


        btngotoSignUp.setOnClickListener {
            var intent = Intent(this, MainActivityRegistration::class.java)
            startActivity(intent)
            finish()
        }

//        forgotPassword.setOnClickListener {
//            var intent = Intent(this, resetPassword::class.java)
//            startActivity(intent)
//            finish()
//        }

    }

    private fun checkUserAccessLevel(uid: String) {
        val df: DocumentReference = fStore.collection("Users").document(uid)
        // extract the data from the document
        df.get().addOnSuccessListener { documentSnapshot ->
            Log.d("TAG", "onSuccess: " + documentSnapshot.data)
            // identify the user access level
            if (documentSnapshot.getString("isAdmin") != null) {
                //user is admin
                startActivity(Intent(applicationContext, requestApproval::class.java))
                finish()
            }
            if (documentSnapshot.getString("isUser") != null) {
                //user is admin
                startActivity(Intent(applicationContext, JobSeekerAvtivity::class.java))
                finish()
            }
        }
        val df2: DocumentReference = fStore.collection("Employers").document(uid)
        // extract the data from the document
        df2.get().addOnSuccessListener { documentSnapshot ->
            Log.d("TAG", "onSuccess: " + documentSnapshot.data)
            // identify the user access level
            if (documentSnapshot.getString("isEmployer") != null) {
                //user is admin
                startActivity(Intent(applicationContext, MainActivityEmployer::class.java))
                finish()
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

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            //startActivity(Intent(applicationContext, MainActivity::class.java))
            checkUserAccessLevel(FirebaseAuth.getInstance().currentUser!!.uid)
            finish()
        }

    }

}