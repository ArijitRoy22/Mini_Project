package com.example.krishismarak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btn_email)
        val btnNumber = findViewById<Button>(R.id.btn_number)
    }


    fun OnClick(view: View) {
        intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
        finish()
    }

    fun OnClick1(view: View) {
        intent = Intent(applicationContext, LoginPhoneNumber::class.java)
        startActivity(intent)
        finish()
    }
}