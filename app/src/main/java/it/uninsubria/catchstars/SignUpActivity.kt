package it.uninsubria.catchstars

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    private lateinit var database:FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private lateinit var insertEmail: EditText
    private lateinit var insertUsername: EditText
    private lateinit var insertPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var SignUpButton: Button
    private lateinit var BackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference

        insertUsername = findViewById(R.id.user)
        insertEmail = findViewById(R.id.email)
        insertPassword = findViewById(R.id.pass)
        confirmPassword = findViewById(R.id.confirm_pass)
        SignUpButton = findViewById(R.id.register)

        //inserimento dati
        val email = insertEmail.text.toString()
        val user = insertUsername.text.toString()
        val pass = insertPassword.text.toString()
        val confirm = confirmPassword.text.toString()

        if (TextUtils.isEmpty(email)){
            Toast.makeText(applicationContext, "Inserire email", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(user)){
            Toast.makeText(applicationContext, "Inserire identificativo", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(pass) || pass.length < 8) {
            Toast.makeText(applicationContext, "Codice di accesso non valido", Toast.LENGTH_LONG).show()
            return
        }
        if(TextUtils.isEmpty(confirm)){
            Toast.makeText(applicationContext, "Conferma il tuo codice di accesso", Toast.LENGTH_LONG).show()
            return
        }
        if(pass != confirm){
            Toast.makeText(applicationContext, "Codice di accesso errato!", Toast.LENGTH_LONG).show()
            return
        }

        //pulsante sign up
        SignUpButton.setOnClickListener{
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(applicationContext, "Registrazione completata! Benvenuto a bordo!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java )
                        startActivity(intent)
                    }
                }
        }

        //pulsante back
        BackButton.setOnClickListener{
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
