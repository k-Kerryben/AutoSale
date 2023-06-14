package com.example.garike

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var eEmail: EditText
    private lateinit var ePass: EditText
    private lateinit var blogin: Button
    private lateinit var bCreate: Button
    private lateinit var fAuth: FirebaseAuth
    private lateinit var progress: ProgressDialog
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        eEmail = findViewById(R.id.eEmailL)
        ePass = findViewById(R.id.ePassL)
        blogin = findViewById(R.id.bSigninL)
        bCreate = findViewById(R.id.bcreateL)
        progress.setTitle("Loading")
        progress.setMessage("Wait a minute..")
        progress = ProgressDialog(applicationContext)

        blogin.setOnClickListener {
            // start by receiving data from user
            var email = eEmail.text.toString().trim()
            var password = ePass.text.toString().trim()
            // check if user is submitting empty field
            if (email.isEmpty()) {
                eEmail.error = "please fill this input"
                eEmail.requestFocus()
            } else if (password.isEmpty()) {
                ePass.error = "please fill this input"
                ePass.requestFocus()
            } else {
                // proceed to register the user
                progress.show()
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show()
                        //mAuth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }else{
                        displayMessage("ERROR", it.exception!!.message.toString())
                    }
                }
            }
        }
    }
    fun displayMessage(title:String, message:String){
        var alertDialog= AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("ok", null )
        alertDialog.create().show()
    }
}