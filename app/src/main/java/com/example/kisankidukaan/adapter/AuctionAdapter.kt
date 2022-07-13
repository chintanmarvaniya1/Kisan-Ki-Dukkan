package com.example.kisankidukaan.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kisankidukaan.R
import com.example.kisankidukaan.models.cropDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_tread_item.view.*

class AuctionAdapter(var cropItemList: ArrayList<cropDetails>) : RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder>() {

    var onItemClick: ((id: cropDetails) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_tread_item, parent, false)
        return AuctionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AuctionViewHolder, position: Int) {
        cropItemList[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
       return cropItemList.size
    }

    inner class AuctionViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView){
        @SuppressLint("ResourceAsColor")
        fun bind(it: cropDetails) = with(itemView){

            val IMGURL = Uri.parse(it.image1)

            if (IMGURL == null) {
                ivCropImage.setBackgroundResource(R.drawable.background)
            } else {
                Picasso.get().load(IMGURL).into(ivCropImage)
            }

            etTitle.text = it.title

            val quantity = it.quantity.split(" ")
            etQuantity.text = "Quantity: ${quantity[0]} Kg"

            etPrice.text = it.price

            if (it.availability) {
                etAvailability.text = "In Stock"
                etAvailability.setTextColor(Color.parseColor("#4CAF50"))

            }else{
                etAvailability.text = "Out Of Stock"
                etAvailability.setTextColor(Color.parseColor("#ffcc0000"))
            }

            etCategory.text = it.type


            setOnClickListener{
                cropItemList.get(adapterPosition).let { it1->
                    onItemClick?.invoke(it1)
                }

            }

        }

    }


}