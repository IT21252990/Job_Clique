package com.example.jobclique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

class EmployerRegistrationNextPart : AppCompatActivity() {
    private lateinit var companyName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerBtn: Button
    private lateinit var goToLogin: TextView
    private var valid = true
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_registration_next_part)

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        companyName = findViewById(R.id.empRegiCpmpanyName)
        email = findViewById(R.id.empRegiEmail)
        phone = findViewById(R.id.empRegiPhone)
        password = findViewById(R.id.empRegiPassword)
        confirmPassword = findViewById(R.id.empRegiConfirmPW)
        registerBtn = findViewById(R.id.btnEmpRegister)
        goToLogin = findViewById(R.id.btnGotoLogin2)


        registerBtn.setOnClickListener {
            checkField(companyName)
            checkField(email)
            checkField(phone)
            checkField(password)
            checkField(confirmPassword)

            val checkCompanyName = companyName.text.toString().trim()
            val checkEmail = email.text.toString().trim()
            val checkPhone = phone.text.toString().trim()
            val checkPassword = password.text.toString().trim()
            val checkConfirmPassword = confirmPassword.text.toString().trim()

            if (checkCompanyName.isEmpty()) {
                companyName.error = ("companyName Required !")
                companyName.requestFocus()
                return@setOnClickListener
            }

            if (checkEmail.isEmpty()) {
                email.error = ("Email Required !")
                email.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches()){
                email.error = ("Please Enter valid Email !")
                email.requestFocus()
                return@setOnClickListener
            }

            if (checkPhone.isEmpty()) {
                phone.error = ("Phone Number Required !")
                phone.requestFocus()
                return@setOnClickListener
            }

            if(checkPhone.length != 10){
                phone.error = ("Phone Number Should Have 10 digits !")
                phone.requestFocus()
                return@setOnClickListener
            }

            if (checkPassword.isEmpty()) {
                password.error = ("Password Required !")
                password.requestFocus()
                return@setOnClickListener
            }

            if (checkConfirmPassword.isEmpty()) {
                confirmPassword.error = ("Confirm Password Required !")
                confirmPassword.requestFocus()
                return@setOnClickListener
            }

            val pattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")

            if(!pattern.matcher(checkPassword).matches()){
                password.error = ("Password not Valid !")
                password.requestFocus()
                return@setOnClickListener
            }

            if(checkConfirmPassword != checkPassword){
                confirmPassword.error = ("Confirm Password mismatched !")
                confirmPassword.requestFocus()
                return@setOnClickListener
            }

            if (valid) {
                // Start the user registration process
                fAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnSuccessListener { authResult: AuthResult ->
                        val user: FirebaseUser? = fAuth.currentUser
                        Toast.makeText(this@EmployerRegistrationNextPart, "Account Created", Toast.LENGTH_SHORT).show()
                        val df: DocumentReference = fStore.collection("Employers").document(user!!.uid)
                        val userInfo: MutableMap<String, Any> = HashMap()
                        userInfo["CompanyName"] = companyName.text.toString()
                        userInfo["UserEmail"] = email.text.toString()
                        userInfo["UserPhone"] = phone.text.toString()

                        // Specify if the user is an admin
                        userInfo["isEmployer"] = "1"

                        df.set(userInfo)
                        startActivity(Intent(applicationContext, MainActivityEmployer::class.java))
                        finish()
                    }
                    .addOnFailureListener { e: Exception ->
                        Toast.makeText(this@EmployerRegistrationNextPart, "Failed to Create Account", Toast.LENGTH_SHORT).show()
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