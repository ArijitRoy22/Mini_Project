package com.example.homeview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.homeview.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        val uid = auth.currentUser?.uid
        if(user == null){
            intent = Intent(applicationContext,Login::class.java)
            startActivity(intent)
            finish()
        }
        else{

        }

        uid?.let{
            database.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    val userName = user?.firstName ?: "Default Username"
                    binding.tvUserName.text = userName
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }

        binding.btnRead.setOnClickListener {
            readData()
        }
        databaseListener()

    }

    private fun databaseListener() {
        database = FirebaseDatabase.getInstance().getReference()
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val nitrogen = snapshot.child("Sensor/Nitrogen").value
                val potassium = snapshot.child("Sensor/Potassium").value
                val phosphorus = snapshot.child("Sensor/Phosphorus").value
                binding.nitrogenValue.text = nitrogen.toString()
                binding.potassiumValue.text = potassium.toString()
                binding.phosphorusValue.text = phosphorus.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to read data from senor", Toast.LENGTH_SHORT).show()
            }
        }
        database.addValueEventListener(postListener)
    }

    private fun readData() {
        database = FirebaseDatabase.getInstance().getReference("Sensor")

        // Access each child value separately
        database.child("Nitrogen").get().addOnSuccessListener { nitrogenSnapshot ->
            if (nitrogenSnapshot.exists()) {
                val nitrogen = nitrogenSnapshot.value.toString().toInt()
                binding.nitrogenValue.text = nitrogen.toString()
            } else {
                Toast.makeText(this, "Sensor/Nitrogen doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve nitrogen data", Toast.LENGTH_SHORT).show()
        }

        // Similar logic for potassium and phosphorus
        database.child("Potassium").get().addOnSuccessListener { potassiumSnapshot ->
            if (potassiumSnapshot.exists()) {
                val potassium = potassiumSnapshot.value.toString().toInt()
                binding.potassiumValue.text = potassium.toString()
            } else {
                Toast.makeText(this, "Sensor/Potassium doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve potassium data", Toast.LENGTH_SHORT).show()
        }

        database.child("Phosphorus").get().addOnSuccessListener { phosphorusSnapshot ->
            if (phosphorusSnapshot.exists()) {
                val phosphorus = phosphorusSnapshot.value.toString().toInt()
                binding.phosphorusValue.text = phosphorus.toString()
            } else {
                Toast.makeText(this, "Sensor/Phosphorus doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve phosphorus data", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClick(view: View){
        FirebaseAuth.getInstance().signOut()
        intent = Intent(applicationContext,Login::class.java)
        startActivity(intent)
        finish()
    }
}