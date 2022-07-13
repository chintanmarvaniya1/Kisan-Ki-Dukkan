package com.example.kisankidukaan.screens.fragments

import android.R.attr.path
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.example.kisankidukaan.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_item_description.*
import kotlinx.android.synthetic.main.single_tread_item.*
import kotlinx.android.synthetic.main.single_tread_item.view.*
import java.io.File
import java.lang.Exception


class ItemDescriptionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id: String? = null
        var cropId: String? = null
        val mDataBaseRef = Firebase.firestore


        var phone : String? = null




        val bundle = this.arguments
        if (bundle != null) {
            tvTitleDesc.text = bundle.getString("Title")
            val price = bundle.getString("Price")
            tvProductPriceDesc.text = "₹ ${price} /Kg"

            val quantity = bundle.getString("Quantity")
            tvProductQuantityDesc.text = quantity

            tvProductDesc.text = bundle.getString("Description")

            id = bundle.getString("UserID")
            val img1 = bundle.getString("Img1")
            Picasso.get().load(Uri.parse(img1)).into(isItemImage)

            cropId = bundle.getString("CropID")

        }


        mDataBaseRef.collection("user").document(id!!).get().addOnSuccessListener {
            phone = it.data!!.get("phone").toString()
            val firstName = it.data!!.get("firstName").toString()
            val lastName = it.data!!.get("lastName").toString()
            val email = it.data!!.get("email").toString()
            val location = "Nashik"

            tvSellerDetail.text =
                "Name: ${firstName} ${lastName}\nLocation: ${location}\nContact no.:${phone}\nEmail:${email}"
        }


        btnContact.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + phone)
            startActivity(dialIntent)
        }

        btnBuy.setOnClickListener {
            mDataBaseRef.collection("crop").document(cropId!!).update("availability",false)


            val aucFragment =  AuctionFragment()

            val transaction = requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fvFragmentContainer, aucFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("BackToAuction")
                .commit()

        }


    }
}




