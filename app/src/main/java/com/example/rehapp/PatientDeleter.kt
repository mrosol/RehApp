package com.example.rehapp

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PatientDeleter (val mCtx: Context, val layoutResId: Int, val patientList: List<Patient>) : ArrayAdapter<Patient>(mCtx, layoutResId, patientList) {

    lateinit var ref: DatabaseReference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val patientTextView =view.findViewById<TextView>(R.id.patientTextView)
        val deleteTextView =view.findViewById<TextView>(R.id.deleteTextView)

        ref = FirebaseDatabase.getInstance().getReference("patients")


        val patient = patientList[position]
        val id = patient.id

        patientTextView.text = patient.name

        deleteTextView.setOnClickListener {
            showDeleteDialogue(id)
            val intent = Intent(mCtx,MainActivity::class.java)
            startActivity(mCtx, intent, intent.extras)
        }

        return view
    }
    private fun showDeleteDialogue(id: String) {

        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Are you sure you want to delete this patient?")

        //val inflater = LayoutInflater.from(mCtx)

        builder.setPositiveButton("DeletePatient patient") { dialog, which ->

            ref.child(id).setValue(null).addOnCompleteListener {
                Toast.makeText(mCtx,"Patient deleted successfully", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("No") { dialog, which -> }

        val alert = builder.create()
        alert.show()
    }
}