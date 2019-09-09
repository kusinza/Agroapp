package adam.rao.agroapp.utils

import adam.rao.agroapp.R
import adam.rao.agroapp.SignInActivity
import adam.rao.agroapp.SignUpActivity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

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

fun attachFirebaseAuthStateListener(arg: FirebaseAuth.AuthStateListener) {
    FirebaseAuth.getInstance().addAuthStateListener(arg)
}

fun dettachFirebaseAuthStateListener(arg: FirebaseAuth.AuthStateListener) {
    FirebaseAuth.getInstance().removeAuthStateListener(arg)
}

fun userHasNoAccount(context: Context) {
    val intent = Intent(context, SignUpActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
}

fun userHasAccountButEmailNotVerified(context: Context) {
    val intent = Intent(context, SignInActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
}