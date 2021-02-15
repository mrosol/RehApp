package com.example.rehapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.logintherapist_layout.*

class LoginTherapist : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logintherapist_layout)

        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            checkLogin()
            auth.signInWithEmailAndPassword(emailTextView.text.toString(), passwordTextView.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(baseContext, "Nie posiadasz odpowiednich uprawnień. Skontaktuj się z administratorem",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun checkLogin() {
        if(emailTextView.text.toString().isEmpty()){
            emailTextView.error = "Podaj swój adres e-mail"
            emailTextView.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailTextView.text.toString()).matches()){
            emailTextView.error = "Podaj prawidłowy adres e-mail"
            emailTextView.requestFocus()
            return
        }

        if(passwordTextView.text.toString().isEmpty()){
            passwordTextView.error = "Podaj swój adres e-mail"
            passwordTextView.requestFocus()
            return
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
       // val currentUser = auth.currentUser
        updateUI(null)
        passwordTextView.setText("")
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if(currentUser != null){
            val intent = Intent(this@LoginTherapist,PatientList::class.java)
            ContextCompat.startActivity(this@LoginTherapist, intent, intent.extras)
        } else {

        }

    }
}
