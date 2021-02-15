package com.example.rehapp

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TrainingAdapter (val mCtx: Context, val layoutResId: Int, val trainingList: List<Training>) : ArrayAdapter<Training>(mCtx, layoutResId, trainingList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val listedTrainingsTextView =view.findViewById<TextView>(R.id.listedTrainingsTextView)

        val training = trainingList[position]

        listedTrainingsTextView.text = training.date

        listedTrainingsTextView.setOnClickListener {
            val intent = Intent(mCtx,ParamList::class.java)
            intent.putExtra("pulse", training.pulse.toString())
            intent.putExtra("sysPressure", training.sysPressure.toString())
            intent.putExtra("diasPressure", training.diasPress.toString())
            intent.putExtra("temperature", training.temperature.toString())
            startActivity(mCtx, intent, intent.extras)
        }

        return view
    }
}