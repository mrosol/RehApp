package com.example.rehapp


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.*

class TrainingList : AppCompatActivity() {

    lateinit var trainingListView: ListView
    lateinit var trainingList: MutableList<Training>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.traininglist_layout)

        trainingList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("patients")
        trainingListView = findViewById(R.id.trainingListView)

        var intent = intent
        val name = intent.getStringExtra("name")

        updateTrainingList(name)
    }

    private fun updateTrainingList(name: String) {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    trainingList.clear()

                    for (h in p0.children) {
                        val patient = h.getValue(Patient::class.java)
                        if (patient!!.name == name) {
                            trainingList = patient.trainingList.toMutableList()
                            break
                        }
                    }
                }

                val adapter = TrainingAdapter(this@TrainingList, R.layout.training_layout, trainingList)
                trainingListView.adapter = adapter
            }
        })
    }
}
