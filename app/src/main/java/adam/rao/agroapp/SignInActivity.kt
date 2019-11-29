package adam.rao.agroapp

import adam.rao.agroapp.utils.ViewDialog
import adam.rao.agroapp.utils.checkEmailAndPasswordNotEmpty
import adam.rao.agroapp.utils.resetPassword
import adam.rao.agroapp.utils.signInUser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var signUpLink: TextView
    private lateinit var forgotPasswordLink: TextView
    private lateinit var etPassword: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSignIn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var linearCont:LinearLayout
    internal var handler = Handler()
    internal var runnable: Runnable = Runnable {
        linearCont.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        handler.postDelayed(runnable,2000)
        val animation12 = AlphaAnimation(0.1f, 1f)
        animation12.setDuration(5000)
        animation12.setFillAfter(true)
        welcome_text.startAnimation(animation12)
        login_text.startAnimation(animation12)
        btnSignIn = findViewById(R.id.btnSignIn)
        etEmail = findViewById(R.id.email_addr_input)
        etPassword = findViewById(R.id.password_user_input)
        signUpLink = findViewById(R.id.tv_sign_up_link)
        forgotPasswordLink = findViewById(R.id.tv_forgot_password_link)
        //progressBar = findViewById(R.id.progressBar)

        linearCont=findViewById(R.id.email_cont2)

        signUpLink.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordLink.setOnClickListener {
            startActivity(Intent(this@SignInActivity, PasswordResetActivity::class.java))
        }

        btnSignIn.setOnClickListener {
            //progressBar.visibility = View.VISIBLE
            if(checkEmailAndPasswordNotEmpty(this@SignInActivity, etEmail.text.toString(), etPassword.text.toString()) == true) {
                signInUser(etEmail.text.toString(), etPassword.text.toString(), this@SignInActivity)
            }
        }
    }
}
