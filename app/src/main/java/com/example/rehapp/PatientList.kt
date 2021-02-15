package com.example.rehapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.*
import com.google.firebase.database.*

class PatientList : AppCompatActivity() {

    lateinit var newButton: Button
    lateinit var deleteButton: Button
    lateinit var patientListView: ListView
    lateinit var patientList: MutableList<Patient>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientlist_layout)

        patientList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("patients")
        newButton = findViewById(R.id.newButton)
        deleteButton = findViewById(R.id.deleteButton)
        patientListView = findViewById(R.id.patientListView)

        newButton.setOnClickListener {
            showAddDialogue()
        }

        deleteButton.setOnClickListener {
            val intent = Intent(this@PatientList,DeletePatient::class.java)
            ContextCompat.startActivity(this@PatientList, intent, intent.extras)
        }

        updatePatientList()
    }

    private fun updatePatientList() {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    patientList.clear()
                    for(h in p0.children){
                        val patient = h.getValue(Patient::class.java)
                        patientList.add(patient!!)
                    }

                    val adapter = PatientAdapter(this@PatientList,R.layout.patient_layout, patientList)
                    patientListView.adapter = adapter
                }
            }

        })

    }

    private fun showAddDialogue() {
        //addPatient()

        val builder =AlertDialog.Builder(this@PatientList)

        builder.setTitle("Add new patient")

        val inflater = LayoutInflater.from(this@PatientList)
        val view = inflater.inflate(R.layout.add_layout,null)

        val patientNameText = view.findViewById<EditText>(R.id.patientNameText)
        val patientSurnameText = view.findViewById<EditText>(R.id.patientSurnameText)


        builder.setView(view)

        builder.setPositiveButton("Add new patient") { dialog, which ->

            val firstName = patientNameText.text.toString().trim()

            if(firstName.isEmpty()){
                patientNameText.error="Please enter patient's name"
                return@setPositiveButton
            }

            val surname = patientSurnameText.text.toString().trim()

            if(surname.isEmpty()){
                patientSurnameText.error="Please enter patient's surname"
                return@setPositiveButton
            }

            val fullName: String
            fullName = firstName + " " + surname

            val trainingList: MutableList<Training> = mutableListOf()
            trainingList.add(Training("22/05/2019",50,120,80, 36.6F))
            trainingList.add(Training("20/05/2019",50,120,80, 36.6F))

            val patientId = ref.push().key
            val patient = Patient(patientId.toString(), fullName, trainingList)

            ref.child(patientId.toString()).setValue(patient).addOnCompleteListener {
                Toast.makeText(applicationContext,"Patient added successfully", Toast.LENGTH_LONG).show()
            }

        }
        builder.setNegativeButton("No") { dialog, which -> }

        val alert = builder.create()
        alert.show()
    }

}