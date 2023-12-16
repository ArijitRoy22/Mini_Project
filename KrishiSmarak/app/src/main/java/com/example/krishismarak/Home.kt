package com.example.krishismarak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.krishismarak.databinding.ActivityHomeBinding

import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //--------Checking user is present or not----------//
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user == null){
            intent = Intent(applicationContext,Login::class.java)
            startActivity(intent)
            finish()
        }
        else{
            binding.userDetails.text = user.email
        }



    }

    fun onClick(view: View){
        FirebaseAuth.getInstance().signOut()
        intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}