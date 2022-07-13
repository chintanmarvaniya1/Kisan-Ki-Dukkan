package com.example.kisankidukaan.screens.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.example.kisankidukaan.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mDataBaseRef = Firebase.firestore

        mDataBaseRef.collection("user").document(mAuth.uid!!).addSnapshotListener { value, error ->
            val email = value!!.data!!["email"]
            val firstName = value!!.data!!["firstName"]
            val lastName = value!!.data!!["lastName"]
            val phoneNo = value!!.data!!["phone"]
            val img = value!!.data!!["image"]

            tvEmailAddress.text = email.toString()
            tvUserNameProfile.text = "${firstName} ${lastName}"

            val bundle = arguments
            val cityName = bundle!!.getString("City")


            tvUserLocation.text = cityName
            tvPhoneNumber.text = phoneNo.toString()
            Picasso.get().load(Uri.parse(img.toString())).into(ivUserPic)

        }

        /*btnUpdate.setOnClickListener {
            val updateInfo = UpdateInformationFragment()
            val transaction = requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fvFragmentContainer, updateInfo)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("BackToAuction")
                .commit()
        }*/

        btnUpdate.isVisible = false


    }
}