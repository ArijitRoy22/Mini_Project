package com.example.krishismarak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.krishismarak.databinding.ActivityHomeBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRead.setOnClickListener{

            val userName: String = "arijit"
            if(userName.isNotEmpty()){
                readData(userName)
            }else {
                Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show()
            }
        }

        //--------Checking user is present or not----------//
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user == null){
            intent = Intent(applicationContext,Login::class.java)
            startActivity(intent)
            finish()
        }
        else{
        }



    }

    private fun readData(userName: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        Log.d("TAG", "Attempting to read data for user: $userName")
        database.child(userName).get().addOnSuccessListener { snapshot ->
            if(snapshot.exists()) {
                Log.d("TAG", "Snapshot exists")
                val firstName = snapshot.child("firstName").value
                val lastName = snapshot.child("lastName").value
                val age = snapshot.child("age").value

                Log.d("TAG", "Data retrieved successfully")
                // Rest of your code for updating UI

            } else {
                Log.d("TAG", "Snapshot does not exist")
                Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Log.e("TAG", "Failed to read data: ${exception.message}")
            Toast.makeText(this, "Failed to read data", Toast.LENGTH_SHORT).show()
        }
    }


    fun onClick(view: View){
        FirebaseAuth.getInstance().signOut()
        intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}