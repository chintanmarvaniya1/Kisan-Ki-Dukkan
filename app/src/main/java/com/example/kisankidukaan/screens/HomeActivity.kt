package com.example.kisankidukaan.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.kisankidukaan.R
import com.example.kisankidukaan.screens.fragments.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth


    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    var city :String? = null
    var lati :String? = null
    var longi :String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mAuth = Firebase.auth
        val mDataBaseRef = Firebase.firestore
        val uid = mAuth.currentUser!!.uid



        replaceFragment(AuctionFragment())
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miWeather -> replaceFragment(WeatherFragment())
                R.id.miShopping -> replaceFragment(AuctionFragment())
                R.id.miAPMC -> replaceFragment(ApmcFragment())
                R.id.miUserAccount -> replaceFragment(UserFragment())
                R.id.miPrediction -> replaceFragment(CropPredictionFragment())
            }
            true
        }



        mDataBaseRef.collection("user").document(uid).addSnapshotListener { value, error ->
            val user = value!!.data!!["lastName"]


            Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()


        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }



    private fun replaceFragment(fragment: Fragment) {
        val mBundle = Bundle()
        mBundle.putString("City",city)
        mBundle.putString("Latitude",lati)
        mBundle.putString("Longitude",longi)
        fragment.arguments = mBundle


        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fvFragmentContainer, fragment)
        transaction.commit()

    }

    public override fun onStart(){
        super.onStart()

        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        }
        else {
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result

                // Toast.makeText(this,"${lastLocation!!.latitude.toString()} ${lastLocation!!.longitude.toString()} ",Toast.LENGTH_LONG).show()
                //latitudeText!!.text = latitudeLabel + ": " + (lastLocation)!!.latitude
                //longitudeText!!.text = longitudeLabel + ": " + (lastLocation)!!.longitude
                lati = lastLocation!!.latitude.toString()
                longi = lastLocation!!.longitude.toString()


                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses: List<Address> = geocoder.getFromLocation(lastLocation!!.latitude, lastLocation!!.longitude, 1)
                city = addresses[0].locality



            }
            else {
                Toast.makeText(this,"${task.exception}",Toast.LENGTH_LONG).show()
                // Log.w(TAG, "getLastLocation:exception", task.exception)
                //showMessage("No location detected. Make sure location is enabled on the device.")
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            //Log.i(TAG, "Displaying permission rationale to provide additional context.")
            //showSnackbar("Location permission is needed for core functionality", "Okay",
            View.OnClickListener {
                startLocationPermissionRequest()
            }
        }
        else {
            //Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    //Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {
                    //showSnackbar("Permission was denied", "Settings",
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            Build.DISPLAY, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
            }
        }
    }

}