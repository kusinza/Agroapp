package adam.rao.agroapp

import adam.rao.agroapp.utils.checkEmailAndPasswordNotEmpty
import adam.rao.agroapp.utils.checkPasswordAndConfirmPasswordMatch
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {

    private lateinit var signInLink: TextView
    private lateinit var signUpBtn: Button
    private lateinit var etUsername: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private var passwordsMatch: Boolean? = null
    private var emailPasswordFieldNotEmpty: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
