package adam.rao.agroapp

import adam.rao.agroapp.utils.resetPassword
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class PasswordResetActivity : AppCompatActivity() {

    private lateinit var btnResetPassword: Button
    private lateinit var etEmail: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        btnResetPassword = findViewById(R.id.btnSendPasswordResetLink)
        etEmail = findViewById(R.id.email_input)

        btnResetPassword.setOnClickListener {
            resetPassword(etEmail.text.toString(),this@PasswordResetActivity)
        }
    }
}
