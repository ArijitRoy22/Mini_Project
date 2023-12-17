package com.example.homeview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homeview.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnResetPassword.setOnClickListener {
            val email = binding.email.text.toString()
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                Toast.makeText(this, "Please check your email",Toast.LENGTH_SHORT ).show()
                startActivity(Intent(this,Login::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
}