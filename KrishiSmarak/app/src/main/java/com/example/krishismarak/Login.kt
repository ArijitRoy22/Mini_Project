package com.example.krishismarak

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.krishismarak.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
    }

    fun onClick1(view: View) {
        intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }


    fun onClick(view: View) {
        val email: String = binding.email.text.toString()
        val passWord: String = binding.password.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, passWord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    Toast.makeText(
                        applicationContext,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    intent = Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "Create a User. ",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

}