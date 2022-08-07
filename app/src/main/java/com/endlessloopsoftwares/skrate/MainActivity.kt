package com.endlessloopsoftwares.skrate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var myFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myFirebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if(myFirebaseAuth.currentUser == null) {
            val loginActivityIntent = Intent(this,LoginActivity::class.java)
            startActivity(loginActivityIntent)
            finish()
        } else {
            val homeActivityIntent = Intent(this,HomeActivity::class.java)
            startActivity(homeActivityIntent)
            finish()
        }
    }
}