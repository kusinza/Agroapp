package adam.rao.agroapp.utils

import adam.rao.agroapp.R
import android.content.Context
import android.widget.Toast

fun checkEmailAndPasswordNotEmpty(context: Context, email: String, password: String): Boolean {
    return if(email.isNotEmpty() && password.isNotEmpty()) {
        true
    } else {
        Toast.makeText(context, context.getString(R.string.empty_email_and_pass_fields), Toast.LENGTH_LONG).show()
        false
    }
}

fun checkPasswordAndConfirmPasswordMatch(context: Context, password: String, confirmPassword: String): Boolean {
    if(password.isNotEmpty() && confirmPassword.isNotEmpty()) {
        if(password == confirmPassword) {
            return true
        } else {
            Toast.makeText(context, context.getString(R.string.password_confirm_password_not_match), Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, context.getString(R.string.empty_email_and_pass_fields), Toast.LENGTH_LONG).show()
    }

    return false
}
