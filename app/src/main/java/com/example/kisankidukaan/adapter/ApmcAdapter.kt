package com.example.kisankidukaan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kisankidukaan.R
import com.example.kisankidukaan.models.APMCCustomRecords
import kotlinx.android.synthetic.main.apmc_snippet.view.*

class ApmcAdapter(val customRecords: List<APMCCustomRecords>) : RecyclerView.Adapter<ApmcAdapter.ApmcViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApmcViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apmc_snippet, parent, false)
        return ApmcViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApmcViewHolder, position: Int) {
        //customRecords[position].let { holder.bind(it) }

        val main =customRecords[position]
        holder.apmcName.text = main.market
        holder.apmcLocation.text = main.district+","+main.state

        for (i in main.commodity) {
            holder.comodity.text = holder.comodity.text .toString()+ i + "\n"
        }
        holder.comodity.text = holder.comodity.text.toString().trimEnd()

        for (i in main.min_price){
            holder.min.text = holder.min.text.toString() + i + "\n"
        }
        holder.min.text = holder.min.text.toString().trimEnd()

        for(i in main.max_price){
            holder.max.text = holder.max.text.toString() + i + "\n"
        }
        holder.max.text = holder.max.text.toString().trimEnd()
    }

    override fun getItemCount(): Int {
        return customRecords.size
    }


    inner class ApmcViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")

        val apmcName =itemView.apmcNameValue
        val apmcLocation   = itemView.apmcLocationValue
        val comodity = itemView.comodityname
        val min = itemView.minvalue
        val max = itemView.maxvalue


        fun bind(it: APMCCustomRecords) = with(itemView) {


/*
            apmcNameValue.text = it.market
            apmcLocationValue.text = it.district+","+it.state

            var comodityText = ""
            for (i in 0..customRecords.size-1){
                comodityText = comodityText + customRecords[i].commodity + "\n"
            }

            comodityname.text = comodityText


            var minValueText = ""
            for (i in 0..customRecords.size-1){
                minValueText = minValueText + customRecords[i].min_price + "\n"
            }

            minvalue.text = minValueText


            var maxValueText = ""
            for (i in 0..customRecords.size-1){
                maxValueText = maxValueText + customRecords[i].max_price + "\n"
            }

            maxvalue.text = maxValueText

*/


            apmcNameValue.text = it.market
            apmcLocationValue.text = it.district +","+it.state

            var commodity = itemView.findViewById<TextView>(R.id.comodityname)
            var min = itemView.findViewById<TextView>(R.id.minvalue)
            var max = itemView.findViewById<TextView>(R.id.maxvalue)

            for (i in it.commodity) {
                commodity.text = commodity.text .toString()+ i + "\n"
            }
            commodity.text = commodity.text.toString().trimEnd()

            for (i in it.min_price){
                min.text = min.text.toString() + i + "\n"
            }
            min.text = min.text.toString().trimEnd()

            for(i in it.max_price){
                max.text = max.text.toString() + i + "\n"
            }
            max.text = max.text.toString().trimEnd()




            /*for (i in it.commodity) {
                comodityname.text = it.commodity.toString()+ i + "\n"
            }
            val comoText = comodityname.text
            comodityname.text = comoText

            for (i in it.min_price){
                minvalue.text = it.min_price.toString()+  i + "\n"
            }
            val minText = minvalue.text
            minvalue.text = minText

            for (i in it.max_price){
                maxvalue.text = it.max_price.toString()+  i + "\n"
            }
            val maxText = maxvalue.text
            maxvalue.text = maxText

             */
        }

    }


}