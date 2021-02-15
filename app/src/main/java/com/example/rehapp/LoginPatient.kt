package com.example.rehapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*

class LoginPatient : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var loginPatientButton: Button
    lateinit var patientLoginTextView: TextView
    lateinit var currentPatientName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpatient_layout)


        ref = FirebaseDatabase.getInstance().getReference("patients")
        loginPatientButton = findViewById(R.id.loginPatientButton)
        patientLoginTextView = findViewById(R.id.patientLoginTextView)

        currentPatientName = ""



        loginPatientButton.setOnClickListener {
           checkName(patientLoginTextView.text.toString())
        }


    }

    private fun checkName(name: String) {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {

                    for (h in p0.children) {
                        val patient = h.getValue(Patient::class.java)
                        if (patient!!.name == name) {
                            currentPatientName = name
                            break
                        }
                    }
                    if(currentPatientName.isEmpty() || currentPatientName==""){
                        patientLoginTextView.error = "Podane imię nie figuruje w bazie. Wprowadź poprawne imię lub skontaktuj się z Administratorem"
                        patientLoginTextView.requestFocus()
                    } else {
                        val intent = Intent(this@LoginPatient,TrainingParams::class.java)
                        intent.putExtra("name", currentPatientName)
                        ContextCompat.startActivity(this@LoginPatient, intent, intent.extras)
                        patientLoginTextView.setText("")
                        currentPatientName = ""
                    }
                }
            }
        })
    }
}
