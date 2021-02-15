package com.example.rehapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class PatientAdapter (val mCtx: Context, val layoutResId: Int, val patientList: List<Patient>) : ArrayAdapter<Patient>(mCtx, layoutResId, patientList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val patientTextView =view.findViewById<TextView>(R.id.patientTextView)
        val trainingTextView =view.findViewById<TextView>(R.id.trainingTextView)

        val patient = patientList[position]

        patientTextView.text = patient.name


        trainingTextView.setOnClickListener {
            val intent = Intent(mCtx,TrainingList::class.java)
            intent.putExtra("name", patient.name)
            startActivity(mCtx,intent,intent.extras)
        }

        patientTextView.setOnClickListener {
            showUpdateDialogue(patient)
        }


        return view
    }
    private fun showUpdateDialogue(patient: Patient) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit name")


        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.add_layout,null)

        val textName = view.findViewById<EditText>(R.id.patientNameText)
        val textSurname  = view.findViewById<TextView>(R.id.patientSurnameText)


        textName.setText("Enter patient's updated name:")
        textName.isFocusable = false
        textSurname.text = patient.name

        builder.setView(view)

        builder.setPositiveButton("Edit") { dialog, which ->

            val db = FirebaseDatabase.getInstance().getReference("patients")

            val name = textSurname.text.toString().trim()
            if(name.isEmpty()){
                textSurname.error="Please enter a name"
                return@setPositiveButton
            }

            val patient = Patient(patient.id, name, patient.trainingList )

            db.child(patient.id).setValue(patient)

            Toast.makeText(mCtx, "Patient's name updated", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No") { dialog, which -> }


        val alert = builder.create()
        alert.show()
    }

}