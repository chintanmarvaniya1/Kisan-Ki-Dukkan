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
import com.example.kisankidukaan.R
import com.example.kisankidukaan.adapter.WeatherAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*


class LoginActivity : AppCompatActivity() {

    var permissionArrays = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    lateinit var mAuth: FirebaseAuth
    lateinit var Adapter: WeatherAdapter


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        //setupPermissions()

        mAuth = FirebaseAuth.getInstance()





        btnLogin.setOnClickListener {
            val email = etLoginEmail.text.toString().trim()
            val password = etLoginPassword.text.toString().trim()

            //Toast.makeText(this, "Successfully Logged in :)", Toast.LENGTH_LONG).show()
            logInUser(email,password)


        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }




    private fun logInUser(email: String, password: String) {
        if (!email.isEmpty() && !password.isEmpty()) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Logging In")
            progressDialog.setCancelable(false)
            progressDialog.show()

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener ( this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this,HomeActivity::class.java)
                    //intent.putExtra("Location",city)
                    startActivity(intent)

                    Toast.makeText(this, "Successfully Logged!!", Toast.LENGTH_LONG).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                } else {

                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }
            })

        }else {
            Toast.makeText(this, "Please fill the Credentials", Toast.LENGTH_SHORT).show()
        }
    }


}