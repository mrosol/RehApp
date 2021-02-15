package com.example.rehapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var therapistImageView: ImageView
    lateinit var patientImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        therapistImageView = findViewById(R.id.therapistImageView)
        patientImageView = findViewById(R.id.patientImageView)

        therapistImageView.setOnClickListener {
            val intent = Intent(this@MainActivity,LoginTherapist::class.java)
            ContextCompat.startActivity(this@MainActivity, intent, intent.extras)
        }

        patientImageView.setOnClickListener {
            val intent = Intent(this@MainActivity,LoginPatient::class.java)
            ContextCompat.startActivity(this@MainActivity, intent, intent.extras)
        }
    }
}
