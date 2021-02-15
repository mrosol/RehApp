package com.example.rehapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ParamList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.param_layout)

        var intent = intent
        val pulse = intent.getStringExtra("pulse")
        val sysPressure = intent.getStringExtra("sysPressure")
        val diasPressure = intent.getStringExtra("diasPressure")
        val temperature = intent.getStringExtra("temperature")

        val pulseTextView =findViewById<TextView>(R.id.pulseTextView)
        val sysPressTextView =findViewById<TextView>(R.id.sysPressTextView)
        val diasPressTextView =findViewById<TextView>(R.id.diasPressTextView)
        val tempTextView =findViewById<TextView>(R.id.tempTextView)

        pulseTextView.text = pulse
        sysPressTextView.text = sysPressure
        diasPressTextView.text = diasPressure
        tempTextView.text = temperature
    }
}
