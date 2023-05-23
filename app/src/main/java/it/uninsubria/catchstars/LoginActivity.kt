package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity(intent: Intent) : AppCompatActivity() {

    private lateinit var insertEmail: EditText
    private lateinit var insertPass: EditText
    private lateinit var LogInButton: Button
    private lateinit var SignInButton: Button

    private lateinit var auth: FirebaseAuth;

    //creazione dell'activity con collegamento al layout 'login'
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //collegamento a firebase
        auth = FirebaseAuth.getInstance()

        //associazione oggetti layout
        insertEmail = findViewById(R.id.username)
        insertPass = findViewById(R.id.password)
        LogInButton = findViewById(R.id.login)
        SignInButton = findViewById(R.id.register)

        LogInButton.setOnClickListener {

            val email = insertEmail.text.toString()
            val password = insertPass.text.toString()

            //controllo credenziali e accesso alla schermata HomeGame
            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    val intentToMain = Intent(this@LoginActivity, HomeGameActivity::class.java)
                    startActivity(intentToMain)
                }
        }

        //passaggio alla schermata SignIn
        SignInButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}