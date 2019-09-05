package adam.rao.agroapp

import adam.rao.agroapp.utils.checkEmailAndPasswordNotEmpty
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {

    private lateinit var signUpLink: TextView
    private lateinit var forgotPasswordLink: TextView
    private lateinit var etPassword: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSignIn: Button
    private lateinit var mFirebaseAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser? = null
    private lateinit var email: String
    private lateinit var password: String
    private var emailPasswordNotEmpty: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mFirebaseAuth = FirebaseAuth.getInstance()

        btnSignIn = findViewById(R.id.btnSignIn)
        etEmail = findViewById(R.id.email_input)
        etPassword = findViewById(R.id.password_input)
        signUpLink = findViewById(R.id.tv_sign_up_link)

        email = etEmail.text.toString()
        password = etPassword.text.toString()

        signUpLink.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            emailPasswordNotEmpty = checkEmailAndPasswordNotEmpty(this, email, password)

            if(emailPasswordNotEmpty != false) {
                signInUser()
            }
        }
    }

    private fun signInUser() {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful) {
                mFirebaseUser = mFirebaseAuth.currentUser
                if(!mFirebaseUser!!.isEmailVerified) {
                    Toast.makeText(this, "Please verify your email", Toast.LENGTH_LONG).show()
                } else {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }else {
                Toast.makeText(this, "Sign In Failed. Do you have an account?", Toast.LENGTH_LONG).show()
            }
        }
    }
}
