package com.example.kisankidukaan.screens

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.kisankidukaan.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        mAuth = Firebase.auth

        btnSignup.setOnClickListener {

            val email = etRegisterEmail.text.toString().trim()
            val password = etRegisterPassword.text.toString().trim()

            registerUser(email, password)
            etRegisterEmail.text = null
            etRegisterPassword.text = null
        }

        btnGoback.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(email: String, password: String) {
        val mDataBaseRef = Firebase.firestore
        var pic: String? = null

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Registering User")
        progressDialog.setCancelable(false)
        progressDialog.show()

        if (!email.isEmpty() && !password.isEmpty()) {
            val firebaseStorage = FirebaseStorage.getInstance()

            firebaseStorage.reference.child("image/profile.png").downloadUrl.addOnSuccessListener {
                pic = it.toString()
            }.addOnFailureListener {
                pic = "https://firebasestorage.googleapis.com/v0/b/kisan-ki-dukaan-9d208.appspot.com/o/image%2Fblank-profile-picture-g5a8be02c1_640.png?alt=media&token=b7f59932-827f-4c90-9f47-e3d788c98741 "
            }
        }


        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val phoneNo = etRegisterMobile.text.toString().trim()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {

                    try {
                        val items = HashMap<String, Any>()
                        items.put("firstName", firstName)
                        items.put("lastName", lastName)
                        items.put("email", email)
                        items.put("phone", phoneNo)
                        items.put("image", pic!!)


                        // mDataBaseRef.collection("user").add(mAuth.uid!!)
                        mDataBaseRef.collection("user")
                            .document(mAuth.uid!!).set(items).addOnCompleteListener {
                                Toast.makeText(this, "Data Added ", Toast.LENGTH_SHORT).show()
                                if (progressDialog.isShowing) progressDialog.dismiss()
                            }.addOnFailureListener {
                                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                                if (progressDialog.isShowing) progressDialog.dismiss()
                            }
                    } catch (e: Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                        if (progressDialog.isShowing) progressDialog.dismiss()
                    }

                } else {
                    Toast.makeText(
                        this,
                        task.exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}

