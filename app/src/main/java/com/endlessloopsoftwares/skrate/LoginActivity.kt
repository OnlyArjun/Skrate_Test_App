package com.endlessloopsoftwares.skrate

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.endlessloopsoftwares.skrate.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var myViewBinder: ActivityLoginBinding
    private lateinit var myAuthInstance: FirebaseAuth
    private lateinit var myGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewBinder = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(myViewBinder.root)

        setUpGoogleVariables()
        myAuthInstance = FirebaseAuth.getInstance()
        myViewBinder.googleSignInButton.setOnClickListener {
            callGoogleSignIn()
        }
    }

    private fun setUpGoogleVariables() {
        val myGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        myGoogleSignInClient = GoogleSignIn.getClient(this, myGoogleSignInOptions)
    }

    private fun callGoogleSignIn() {
        val signInIntent = myGoogleSignInClient.signInIntent
        myStartActivityForResult.launch(signInIntent)
    }

    private val myStartActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val receivedData = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val accountSignedIn = receivedData.result
                authGoogleIdWithFirebase(accountSignedIn.idToken)
            } catch (exceptionOccurred: ApiException) {
                Snackbar.make(
                    myViewBinder.root,
                    "Unable to Sign In $exceptionOccurred",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun authGoogleIdWithFirebase(idToken: String?) {
        val userCredentials = GoogleAuthProvider.getCredential(idToken, null)
        myAuthInstance.signInWithCredential(userCredentials)
            .addOnCompleteListener(this) { taskReturned ->
                if (taskReturned.isSuccessful) {
                    val homeActivityIntent = Intent(this,HomeActivity::class.java)
                    startActivity(homeActivityIntent)
                    finish()
                } else {
                    Snackbar.make(myViewBinder.root, "Sign In failed", Snackbar.LENGTH_SHORT).show()
                }
            }
    }
}