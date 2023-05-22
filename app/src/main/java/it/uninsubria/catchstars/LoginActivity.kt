package it.uninsubria.catchstars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

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
        auth = Firebase.auth

        //associazione oggetti layout
        insertEmail = findViewById(R.id.username)
        insertPass = findViewById(R.id.password)
        LogInButton = findViewById(R.id.login)
        SignInButton = findViewById(R.id.register)

        LogInButton.setOnClickListener{
            //to do: controllo credenziali e accesso
            val email = insertEmail.text.toString()
            val password = insertPass.text.toString()



            //Toast.maketext permette di creare un popup con testo


            //chiamata metodo per il passaggio a HomeGame
            goHomeGame()
        }

        SignInButton.setOnClickListener{
            //passaggio alla schermata SignIn
            goSignUp()
        }

    }

    //metodo per il passaggio alla schermata home del gioco
    fun goHomeGame(){
        val intent = Intent(this@LoginActivity, HomeGameActivity::class.java)
        HomeGameActivity(intent)
    }

    fun goSignUp(){
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        SignUpActivity(intent)
    }

}