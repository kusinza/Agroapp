package adam.rao.agroapp.utils

import adam.rao.agroapp.MainActivity
import adam.rao.agroapp.R
import adam.rao.agroapp.SignInActivity
import adam.rao.agroapp.SignUpActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

fun checkEmailAndPasswordNotEmpty(context: Context, email: String, password: String): Boolean {
    return if(email.isNotEmpty() && password.isNotEmpty()) {
        true
    } else {
        Toast.makeText(context, context.getString(R.string.empty_email_and_pass_fields), Toast.LENGTH_LONG).show()
        false
    }
    // TODO keeps returning false
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

fun resetPassword(email: String, context: Context) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
        if(task.isSuccessful) {
            Toast.makeText(context, "Password Reset Link sent to Email", Toast.LENGTH_LONG).show()
        }
    }
}

fun resetEmailAddress(email: String, password: String, context: Context) {
    val credentials = EmailAuthProvider.getCredential(FirebaseAuth.getInstance()
        .currentUser!!.email as String, password)
    FirebaseAuth.getInstance().currentUser!!.reauthenticate(credentials).addOnCompleteListener { task ->
        if(task.isSuccessful) {
            FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener { task1 ->
                if(task1.result!!.signInMethods!!.size == 1) {
                    Toast.makeText(context, "Email Already Exists, choose another", Toast.LENGTH_LONG).show()
                } else {
                    FirebaseAuth.getInstance().currentUser!!.updateEmail(email).addOnCompleteListener { task2 ->
                        if(task2.isSuccessful) {
                            Toast.makeText(context, "Email Reset Successful, verification email sent", Toast.LENGTH_LONG).show()
                            FirebaseAuth.getInstance().currentUser!!.sendEmailVerification()
                            signOut()
                        }
                    }
                }
            }
        }
    }.addOnFailureListener { e ->
        Toast.makeText(context, "Failed to Re-authenticate", Toast.LENGTH_LONG).show()
        Log.d("TAG", e.message)
    }
}

fun signInUser(email: String, password: String, context: Context) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
        if(task.isSuccessful) {
            val mFirebaseUser = FirebaseAuth.getInstance().currentUser
            if(!mFirebaseUser!!.isEmailVerified) {
                Toast.makeText(context, "Please verify your email", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }else {
            Toast.makeText(context, "Sign In Failed. Do you have an account?", Toast.LENGTH_LONG).show()
        }
    }
}

fun signUpUser(email: String, password: String, context: Context) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if(task.isSuccessful) {
            Log.d("TAG", "SignUp successful")
            Toast.makeText(context, "Registration Successful, check email for verification link", Toast.LENGTH_LONG).show()
            sendVerificationEmail()
            val intent = Intent(context, SignInActivity::class.java)
            context.startActivity(intent)
            signOut()
        } else {
            Log.d("TAG", "SignUp unsuccessful")
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
        }
    }
}

fun sendVerificationEmail() {
    val mFirebaseUser = FirebaseAuth.getInstance().currentUser
    if(mFirebaseUser != null) {
        if(!mFirebaseUser.isEmailVerified) {
            mFirebaseUser.sendEmailVerification()
        }
    }
}

fun checkUserSignedIn(context: Context): FirebaseAuth.AuthStateListener {
    return FirebaseAuth.AuthStateListener { auth ->
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(context, "Please create an account", Toast.LENGTH_LONG).show()
            userHasNoAccount(context)
        } else {
            if(!user.isEmailVerified) {
                Toast.makeText(context, "Please verify your email address", Toast.LENGTH_LONG).show()
                userHasAccountButEmailNotVerified(context)
            }
        }
    }
}

fun signOut() {
    FirebaseAuth.getInstance().signOut()
}