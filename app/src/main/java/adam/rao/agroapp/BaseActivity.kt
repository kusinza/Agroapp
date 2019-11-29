package adam.rao.agroapp

import adam.rao.agroapp.utils.*
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.nav_header_base.*



class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firebaseAuthStateListener: FirebaseAuth.AuthStateListener
    private lateinit var userName: TextView
    private lateinit var email: TextView
    private val mRequestCode = 101
    private val locationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var  mAuth: FirebaseUser

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        setUpLocation()
        firebaseAuthStateListener = checkUserSignedIn(this@BaseActivity)

        mAuth= getAuthInstance()
        db= FirebaseFirestore.getInstance()
         drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        userName=navView.getHeaderView(0).findViewById<TextView>(R.id.tvUserName)

        email = navView.getHeaderView(0).findViewById<TextView>(R.id.tvEmailAddress)

        setUpUserEmailAndUserNameTexts()


        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.details, R.id.plant_choice,
                R.id.expected_output, R.id.seed_input, R.id.nav_notification
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.base, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.nav_logout -> {
                drawerLayout.closeDrawer(GravityCompat.START);

                signOut()
                true
            }
            R.id.nav_home -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
                drawerLayout.closeDrawer(GravityCompat.START);
                true
            }
            R.id.nav_notification -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_notification)
                drawerLayout.closeDrawer(GravityCompat.START);
                true
            }
            else ->
                true
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

    private fun setUpUserEmailAndUserNameTexts() {
        userName.text = FirebaseAuth.getInstance().currentUser!!.displayName
        email.text = FirebaseAuth.getInstance().currentUser!!.email
    }


    private fun setUpLocation() {
        if(ContextCompat.checkSelfPermission(this, locationPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(locationPermission), mRequestCode)
            return
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnCompleteListener {
                //TODO
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
}
