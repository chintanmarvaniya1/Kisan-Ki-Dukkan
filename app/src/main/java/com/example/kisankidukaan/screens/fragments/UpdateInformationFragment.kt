package com.example.kisankidukaan.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kisankidukaan.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_update_information.*


class UpdateInformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        return inflater.inflate(R.layout.fragment_update_information, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mDataBaseRef = Firebase.firestore
        var img:String? =null

        mDataBaseRef.collection("user").document(mAuth.uid!!).addSnapshotListener { value, error ->
            val email = value!!.data!!["email"]
            val firstName = value!!.data!!["firstName"]
            val lastName = value!!.data!!["lastName"]
            val phoneNo = value!!.data!!["phone"]
            img = value!!.data!!["image"].toString()
            val location = value!!.data!!["location"]
            val about = value!!.data!!["aboutMe"]



        }




        btnUpdateInitialize.setOnClickListener {
            val items = HashMap<String, Any>()
            items.put("firstName", etFirstNameUpdate.text.toString().trim())
            items.put("lastName", etLastNameUpdate.text.toString().trim())
            items.put("email", etEmailUpdate.text.toString().trim())
            items.put("phone", etPhoneNoUpdate.text.toString().trim())
            items.put("image", img.toString())


            mDataBaseRef.collection("user").document(mAuth.uid!!).update("firstName",etFirstNameUpdate.text.toString().trim())

            // mDataBaseRef.collection("user").add(mAuth.uid!!)
           /* mDataBaseRef.collection("user")
                .document(mAuth.uid!!).update(items).addOnSuccessListener {
                    Toast.makeText(requireContext(),"Successful Update",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }*/
        }

        //mDataBaseRef.collection("user").document(mAuth.uid!!).update("email","Kishor")


    }

}