package adam.rao.agroapp

import adam.rao.agroapp.utils.checkEmailAndPasswordNotEmpty
import adam.rao.agroapp.utils.resetPassword
import adam.rao.agroapp.utils.signInUser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {

    private lateinit var signUpLink: TextView
    private lateinit var forgotPasswordLink: TextView
    private lateinit var etPassword: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSignIn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSignIn = findViewById(R.id.btnSignIn)
        etEmail = findViewById(R.id.email_addr_input)
        etPassword = findViewById(R.id.password_user_input)
        signUpLink = findViewById(R.id.tv_sign_up_link)
        forgotPasswordLink = findViewById(R.id.tv_forgot_password_link)
        progressBar = findViewById(R.id.progressBar)

        signUpLink.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordLink.setOnClickListener {
            startActivity(Intent(this@SignInActivity, PasswordResetActivity::class.java))
        }

        btnSignIn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            if(checkEmailAndPasswordNotEmpty(this@SignInActivity, etEmail.text.toString(), etPassword.text.toString()) == true) {
                signInUser(etEmail.text.toString(), etPassword.text.toString(), this@SignInActivity)
                progressBar.visibility = View.INVISIBLE
            }
        }
    }
}
