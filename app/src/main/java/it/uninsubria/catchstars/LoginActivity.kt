package it.uninsubria.catchstars

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var insertEmail: EditText
    private lateinit var insertPass: EditText
    private lateinit var logInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var auth: FirebaseAuth

    //creazione dell'activity con collegamento al layout 'login'
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //collegamento a firebase
        auth = Firebase.auth

        //associazione oggetti layout
        insertEmail = findViewById(R.id.username)
        insertPass = findViewById(R.id.password)
        logInButton = findViewById(R.id.login)
        signUpButton = findViewById(R.id.register)


        logInButton.setOnClickListener {
            signIn()
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(){
        val email = insertEmail.text.toString()
        val password = insertPass.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Inserire identificativo email", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Codice di accesso mancante", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    //val user = auth.currentUser
                    Toast.makeText(applicationContext, "Benvenuto a bordo!", Toast.LENGTH_LONG).show()
                    val goHomeG = Intent(this@LoginActivity, HomeGameActivity::class.java)
                    startActivity(goHomeG)
                    //goHomeGame()//(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Ops! Qualcosa è andato storto", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /* private fun goHomeGame(){ //(user:FirebaseUser?)
        val goHomeG = Intent(this@LoginActivity, HomeGameActivity::class.java)
        startActivity(goHomeG)
    }*/

}