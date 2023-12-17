package com.example.homeview

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.homeview.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        val verification = auth.currentUser?.isEmailVerified
        if (currentUser != null) {
            if(verification == true) {
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{
//            startActivity(Intent(applicationContext,Register::class.java))
//            finish()
            Toast.makeText(this,"You can login now!",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this,ResetPassword::class.java))
            finish()
        }

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
                    val verification = auth.currentUser?.isEmailVerified
                    if(verification == true){
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(
                            applicationContext,
                            "Login Successul",
                            Toast.LENGTH_SHORT
                        ).show()
                        intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"Please verify your email", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "Create a User. ",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

}