package adam.rao.agroapp

import adam.rao.agroapp.utils.checkEmailAndPasswordNotEmpty
import adam.rao.agroapp.utils.checkPasswordAndConfirmPasswordMatch
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {

    private lateinit var signInLink: TextView
    private lateinit var signUpBtn: Button
    private lateinit var etUsername: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var mFirebaseAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser? = null
    private var passwordsMatch: Boolean? = null
    private var emailPasswordFieldNotEmpty: Boolean? = null
    private var TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_sign_up)
        mFirebaseAuth = FirebaseAuth.getInstance()

        signInLink = findViewById(R.id.tv_sign_in_link)
        signUpBtn = findViewById(R.id.btnSignUp)
        etUsername = findViewById(R.id.username_input)
        etEmail = findViewById(R.id.email_input)
        etPassword = findViewById(R.id.password_input)
        etConfirmPassword = findViewById(R.id.confirm_password_input)

        signInLink.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {

            passwordsMatch = checkPasswordAndConfirmPasswordMatch(this,
                etPassword.text.toString(), etConfirmPassword.text.toString())
            emailPasswordFieldNotEmpty = checkEmailAndPasswordNotEmpty(this,
                etEmail.text.toString(), etConfirmPassword.text.toString())

            if(passwordsMatch as Boolean != false && emailPasswordFieldNotEmpty as Boolean != false ) {
                signUpUser(etEmail.text.toString(), etConfirmPassword.text.toString())
            }
        }
    }

    private fun signUpUser(email: String, password: String) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                Log.d(TAG, "SignUp successful")
                Toast.makeText(this, "Registration Successful, check email for verification link", Toast.LENGTH_LONG).show()
                sendVerificationEmail()
                intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                mFirebaseAuth.signOut()
            } else {
                Log.d(TAG, "SignUp unsuccessful")
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendVerificationEmail() {
        mFirebaseUser = mFirebaseAuth.currentUser
        if(mFirebaseUser != null) {
            if(!mFirebaseUser!!.isEmailVerified) {
               mFirebaseUser!!.sendEmailVerification()
            }
        }
    }
}
