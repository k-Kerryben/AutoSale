package com.example.garike

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var bCreate: Button
    private lateinit var bSignin: Button
    private lateinit var ename: EditText
    private lateinit var eemail: EditText
    private lateinit var epass: EditText
    private lateinit var erPass: EditText
    private lateinit var tname: TextView
    private lateinit var temail: TextView
    private lateinit var tpass: TextView
    private lateinit var trPass: TextView
    private lateinit var fAuth: FirebaseAuth
    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bCreate = findViewById(R.id.bCreate)
        bSignin = findViewById(R.id.bLogin)
        ename = findViewById(R.id.eName)
        eemail = findViewById(R.id.eEmail)
        epass = findViewById(R.id.ePass)
        erPass = findViewById(R.id.erPass)
        tname = findViewById(R.id.tName)
        temail = findViewById(R.id.tEmail)
        tpass = findViewById(R.id.tPass)
        trPass = findViewById(R.id.trPass)
        progress.setTitle("Loading.")
        progress.setMessage("Wait a minute.")
        progress = ProgressDialog(applicationContext)
        bCreate.setOnClickListener {

            // start by receiving data from user
            var email = eemail.text.toString().trim()
            var password = epass.text.toString().trim()
            var rpassword = erPass.text.toString().trim()
            var name = ename.text.toString().trim()

            // check if user is submitting empty field
            if (email.isEmpty()){
                eemail.error = "please fill this input"
                eemail.requestFocus()
            }else if (password.isEmpty()){
                epass.error = "please fill this input"
                epass.requestFocus()
            }else if (name.isEmpty()){
                ename.error = "please fill this input"
                ename.requestFocus()
            }else if (rpassword.isEmpty()){
                erPass.error = "please fill this input"
                erPass.requestFocus()}
            else if (rpassword != password){erPass.error = "Passwords do not match!!"
            }else if (password.length < 6){
                epass.error = "Password less than six characters!!"
                epass.requestFocus()
            }

            else{
                // proceed to register the user
                progress.show()
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{

                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show()
                        //fAuth.signOut()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
                        displayMessage("ERROR", it.exception!!.message.toString())
                    }
                }
            }


        }
        bSignin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java)) }
    }
    private fun displayMessage(title:String, message:String){
        var alertDialog= AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("ok", null )
        alertDialog.create().show()
    }

}