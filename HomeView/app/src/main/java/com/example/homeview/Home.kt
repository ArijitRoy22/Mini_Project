package com.example.homeview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.homeview.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        }else{
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
            Toast.makeText(this,"You can login now!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        auth = FirebaseAuth.getInstance()
//        val user = auth.currentUser
//        if(user == null){
//            intent = Intent(applicationContext,Login::class.java)
//            startActivity(intent)
//            finish()
//        }
//        else{
//            intent = Intent(applicationContext,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

    }

    fun onClick(view: View) {
        intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
        finish()
    }

//    fun OnClick1(view: View) {
//        intent = Intent(applicationContext, LoginPhoneNumber::class.java)
//        startActivity(intent)
//        finish()
//    }
}