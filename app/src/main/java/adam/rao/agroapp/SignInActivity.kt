package adam.rao.agroapp

import adam.rao.agroapp.utils.resetPassword
import adam.rao.agroapp.utils.signInUser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {

    private lateinit var signUpLink: TextView
    private lateinit var forgotPasswordLink: TextView
    private lateinit var etPassword: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSignIn: Button
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSignIn = findViewById(R.id.btnSignIn)
        etEmail = findViewById(R.id.email_addr_input)
        etPassword = findViewById(R.id.password_user_input)
        signUpLink = findViewById(R.id.tv_sign_up_link)
        forgotPasswordLink = findViewById(R.id.tv_forgot_password_link)

        email = etEmail.text.toString()
        password = etPassword.text.toString()

        signUpLink.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordLink.setOnClickListener {
            resetPassword(this@SignInActivity)
        }

        btnSignIn.setOnClickListener {
            signInUser(email, password, this@SignInActivity)
//            Log.d("email", email)
//            Log.d("password", password)

            //TODO email and password edittext texts keep on coming empty
        }
    }
}
