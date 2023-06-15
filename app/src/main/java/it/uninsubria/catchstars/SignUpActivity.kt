package it.uninsubria.catchstars

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var insertEmail: EditText
    private lateinit var insertUsername: EditText
    private lateinit var insertPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth

        insertUsername = findViewById(R.id.user)
        insertEmail = findViewById(R.id.email)
        insertPassword = findViewById(R.id.pass)
        confirmPassword = findViewById(R.id.confirm_pass)
        signUpButton = findViewById(R.id.register)

        //pulsante sign up
        signUpButton.setOnClickListener{
            signUp()
        }
    }

    fun signUp(){
        //compilazione campi
        val email = insertEmail.text.toString()
        val user = insertUsername.text.toString()
        val pass = insertPassword.text.toString()
        val confirm = confirmPassword.text.toString()

        //controllo correttenza compilazione
        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(confirm)) {
            Toast.makeText(applicationContext, "Inserire dati mancanti", Toast.LENGTH_LONG).show()
            return
        }

        if (pass.length < 6) {
            Toast.makeText(applicationContext, "Codice di accesso non valido. Inserire almeno 6 caratteri", Toast.LENGTH_LONG).show()
            return
        }

        if(pass != confirm){
            Toast.makeText(applicationContext, "Conferma del codice di accesso errata!", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                Toast.makeText(applicationContext, "Registrazione completata!", Toast.LENGTH_LONG).show()
                goHomeGameActivity()
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Ops! Qualcosa è andato storto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goHomeGameActivity(){
         val intent = Intent(this@SignUpActivity, HomeGameActivity::class.java )
         startActivity(intent)
    }

}
