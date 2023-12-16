package com.example.homeview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.homeview.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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