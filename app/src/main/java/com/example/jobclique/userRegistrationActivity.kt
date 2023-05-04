package com.example.jobclique

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class userRegistrationActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerBtn: Button
    private lateinit var goToLogin: TextView
    private var valid = true
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.registerName)
        email = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        confirmPassword = findViewById(R.id.registerConfirmPassword)
        registerBtn = findViewById(R.id.registerBtn)
        goToLogin = findViewById(R.id.btnApplyJob)

        registerBtn.setOnClickListener {
            checkField(fullName)
            checkField(email)
            checkField(password)
            checkField(confirmPassword)

            if (valid) {
                // Start the user registration process
                fAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnSuccessListener { authResult: AuthResult ->
                        val user: FirebaseUser? = fAuth.currentUser
                        Toast.makeText(this@userRegistrationActivity, "Account Created", Toast.LENGTH_SHORT).show()
                        val df: DocumentReference = fStore.collection("Users").document(user!!.uid)
                        val userInfo: MutableMap<String, Any> = HashMap()
                        userInfo["FullName"] = fullName.text.toString()
                        userInfo["UserEmail"] = email.text.toString()

                        // Specify if the user is an admin
                        userInfo["isUser"] = "1"

                        df.set(userInfo)
                        startActivity(Intent(this, JobSeekerAvtivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { e: Exception ->
                        Toast.makeText(this@userRegistrationActivity, "Failed to Create Account", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        goToLogin.setOnClickListener {
            var intent = Intent(this, userLoginActivity::class.java)
            startActivity(intent)
            finish()
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

}
