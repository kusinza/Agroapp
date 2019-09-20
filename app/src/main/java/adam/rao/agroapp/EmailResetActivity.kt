package adam.rao.agroapp

import adam.rao.agroapp.utils.resetEmailAddress
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class EmailResetActivity : AppCompatActivity() {

    private lateinit var btnResetEmail: Button
    private lateinit var etResetEmail: TextInputEditText
    private lateinit var etPasswordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_reset)

        btnResetEmail = findViewById(R.id.btnResetEmail)
        etResetEmail = findViewById(R.id.email_input)
        etPasswordInput = findViewById(R.id.password_user_input)

        btnResetEmail.setOnClickListener {
            resetEmailAddress(etResetEmail.text.toString(),
                etPasswordInput.text.toString()
                , this@EmailResetActivity)
        }
    }
}
