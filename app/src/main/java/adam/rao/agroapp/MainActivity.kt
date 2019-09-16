package adam.rao.agroapp

import adam.rao.agroapp.models.Details
import adam.rao.agroapp.utils.*
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val mRequestCode = 101
    private val locationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var firebaseAuthStateListener: FirebaseAuth.AuthStateListener
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuthStateListener = checkUserSignedIn(this@MainActivity)
        setUpLocation()

        val plantType = findViewById<TextInputEditText>(R.id.plant_input)
        val seedInput = findViewById<TextInputEditText>(R.id.seed_input)
        val landSizeInput = findViewById<TextInputEditText>(R.id.land_size_input)
        val submitBtn = findViewById<Button>(R.id.btnSubmit)

        submitBtn.setOnClickListener {
            insertToDatabase(FirebaseAuth.getInstance().currentUser!!.uid, Details(
                plantType.text.toString(),
                seedInput.text.toString(),
                landSizeInput.text.toString(),
                location
            ))
        }
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
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnCompleteListener {task ->
                location = task.result as Location
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.general_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.logout -> {
                signOut()
                true
            }
            R.id.reset_email -> {
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }

    }
}