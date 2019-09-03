package adam.rao.agroapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val mRequestCode = 101
    private val locationPermission = Manifest.permission.ACCESS_COARSE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpLocation()

        val seedInput = findViewById<TextInputEditText>(R.id.seed_input)
        val landSizeInput = findViewById<TextInputEditText>(R.id.land_size_input)
        val submitBtn = findViewById<MaterialButton>(R.id.btnSubmit)
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
