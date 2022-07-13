package com.example.kisankidukaan.screens.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kisankidukaan.R
import com.example.kisankidukaan.adapter.AuctionAdapter
import com.example.kisankidukaan.models.cropDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_apmc.*
import kotlinx.android.synthetic.main.fragment_auction.*

class AuctionFragment : Fragment() {

    lateinit var adapter: AuctionAdapter
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
        return inflater.inflate(R.layout.fragment_auction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cropItemList = ArrayList<cropDetails>()
        val mDataBaseRef = Firebase.firestore

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Getting Items")
        progressDialog.setCancelable(false)
        progressDialog.show()

        fabAddItem.setOnClickListener {
            val addItemFragment = AddItemFragment()
            val transaction = requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fvFragmentContainer, addItemFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("addItem")
                .commit()
        }

        mDataBaseRef.collection("crop").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val userID = document.data["userId"].toString()
                    val cropID = document.id.toString()
                    val title = document.data["title"].toString()
                    val description = document.data["description"].toString()
                    val quantity = document.data["quantity"].toString()
                    val price = document.data["price"].toString()
                    val image1 = document.data["image1"].toString()
                    val availability = document.data["availability"]
                    val type = document.data["type"].toString()

                    if (availability as Boolean) {
                        if (userID != mAuth.uid) {
                            cropItemList.add(
                                cropDetails(
                                    cropID,
                                    userID,
                                    availability as Boolean,
                                    title,
                                    description,
                                    quantity,
                                    price,
                                    image1,
                                    type
                                )
                            )
                        }
                    }
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }
                //rvItemsForSell.text = cropItemList.toString()
                adapter = AuctionAdapter(cropItemList)
                rvItemsForSell.adapter = adapter
                rvItemsForSell.layoutManager = LinearLayoutManager(requireContext())

                adapter.onItemClick = {
                    val itemDescFragment = ItemDescriptionFragment()
                    val bundle = Bundle()
                    bundle.putString("CropID", it.cropID)
                    bundle.putString("Title", it.title)
                    bundle.putString("Img1", it.image1)
                    bundle.putString("Price", it.price)
                    bundle.putString("Quantity", it.quantity)
                    bundle.putString("Description", it.description)
                    bundle.putString("UserID", it.userId)

                    itemDescFragment.arguments = bundle

                    val transaction = requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fvFragmentContainer, itemDescFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .setReorderingAllowed(true)
                        .addToBackStack("BackToAuction")
                        .commit()


                }


                //  Toast.makeText(requireContext(),it.documents.toString(),Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            }

        svSearchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchForCrop(cropItemList, query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchForCrop(cropItemList, newText)
                return false
            }
        })

    }

    private fun searchForCrop(cropItemList: java.util.ArrayList<cropDetails>, query: String?) {
        val searchedItem = ArrayList<cropDetails>()
        for (i in 0 until cropItemList.size) {
            if (cropItemList[i].title.contains(query.toString()) || cropItemList[i].description.contains(
                    query.toString()
                ) || cropItemList[i].type.contains(query.toString())
            ) {
                searchedItem.add(cropItemList[i])
            }
        }
        adapter = AuctionAdapter(searchedItem)
        rvItemsForSell.adapter = adapter
        rvItemsForSell.layoutManager = LinearLayoutManager(requireContext())
        adapter.onItemClick = {
            val itemDescFragment = ItemDescriptionFragment()
            val bundle = Bundle()
            bundle.putString("Title", it.title)
            bundle.putString("Img1", it.image1)
            bundle.putString("Price", it.price)
            bundle.putString("Quantity", it.quantity)
            bundle.putString("Description", it.description)
            bundle.putString("UserID", it.userId)

            itemDescFragment.arguments = bundle

            val transaction = requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fvFragmentContainer, itemDescFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("BackToAuction")
                .commit()


            svSearchItem.setQuery("", false)


        }


    }


}