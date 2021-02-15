package com.example.rehapp


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class DeletePatient : AppCompatActivity() {

    lateinit var patientListView: ListView
    lateinit var patientList: MutableList<Patient>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientdelete_layout)

        patientList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("patients")
        patientListView = findViewById(R.id.patientListView)


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

                    val deleter = PatientDeleter(this@DeletePatient,R.layout.patientdelete_layout, patientList)
                    patientListView.adapter = deleter
                }
            }

        })



    }


}
