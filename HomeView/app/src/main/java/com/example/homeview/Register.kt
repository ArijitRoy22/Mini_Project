package com.example.homeview

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.homeview.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
    }

    fun onClick1(view: View){
        intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }


    fun onClick(view: View) {
        val email: String = binding.email.text.toString()
        val passWord: String = binding.password.text.toString()
        val firstName = binding.firstName.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, passWord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid
                    // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success")
//                            val user = auth.currentUser
                    if (uid != null) {
                        val userData = User(firstName)
                        database.child(uid).setValue(userData)
                            .addOnCompleteListener { userCreationTask ->
                                if (userCreationTask.isSuccessful) {
                                    Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT)
                                        .show()
                                    intent = Intent(this, Login::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Failed to save user data",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "Already a User.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}