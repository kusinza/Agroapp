package adam.rao.agroapp

import adam.rao.agroapp.utils.*
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val mRequestCode = 101
    private val locationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var firebaseAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUserSignedIn(this@MainActivity)
        setUpLocation()

        val seedInput = findViewById<TextInputEditText>(R.id.seed_input)
        val landSizeInput = findViewById<TextInputEditText>(R.id.land_size_input)
        val submitBtn = findViewById<Button>(R.id.btnSubmit)
    }

    override fun onResume() {
        super.onResume()
        attachFirebaseAuthStateListener(firebaseAuthStateListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        dettachFirebaseAuthStateListener(firebaseAuthStateListener)
    }

    private fun setUpLocation() {
        if(ContextCompat.checkSelfPermission(this, locationPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(locationPermission), mRequestCode)
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == mRequestCode && grantResults.isNotEmpty()) {
            return
        } else {
            ActivityCompat.shouldShowRequestPermissionRationale(this, locationPermission)
        }
    }
}