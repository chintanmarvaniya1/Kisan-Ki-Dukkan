package com.example.kisankidukaan.screens.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kisankidukaan.R
import com.example.kisankidukaan.adapter.ApmcAdapter
import com.example.kisankidukaan.models.APMCCustomRecords
import com.example.kisankidukaan.network.APMC_API
import kotlinx.android.synthetic.main.fragment_apmc.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ApmcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApmcFragment : Fragment() {


    var Spinner1: Int? = null
    var Spinner2: Int? = null
    var someMap: Map<Any, Array<String>>? = null
    lateinit var adapter: ApmcAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apmc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val states = arrayOf(
            "None",
            "Andhra Pradesh",
            "Chandigarh",
            "Chattisgarh",
            "Gujarat",
            "Hariyana",
            "Himachal Pradesh",
            "Jammu & Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Odisha",
            "Pudu Cherry",
            "Punjab",
            "Rajasthan",
            "Tamil Nadu",
            "Telangana",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"
        )

        val districtInGujarat: Array<String> = arrayOf(
            "None",
            "Ahmedabad",
            "Amreli",
            "Anand",
            "Aravalli",
            "Banaskantha",
            "Bharuch",
            "Bhavnagar",
            "Botad",
            "Chhota Udepur",
            "Dahod",
            "Dangs",
            "Devbhoomi Dwarka",
            "Gandhinagar",
            "Gir Somnath",
            "Jamnagar",
            "Junagadh",
            "Kachchh",
            "Kheda",
            "Mahisagar",
            "Mehsana",
            "Morbi",
            "Narmada",
            "Navsari",
            "Panchmahal",
            "Patan",
            "Porbandar",
            "Rajkot",
            "Sabarkantha",
            "Surat",
            "Surendranagar",
            "Tapi",
            "Vadodara",
            "Valsad"

        )
        var districtInMaha: Array<String> = arrayOf(
            "None",
            "Ahmednagar",
            "Akola",
            "Amravati",
            "Aurangabad",
            "Beed",
            "Bhandara",
            "Buldhana",
            "Chandrapur",
            "Dhule",
            "Gadchiroli",
            "Gondia",
            "Hingoli",
            "Jalgaon",
            "Jalna",
            "Kolhapur",
            "Latur",
            "Mumbai City",
            "Mumbai Suburban",
            "Nagpur",
            "Nanded",
            "Nandurbar",
            "Nashik",
            "Osmanabad",
            "Palghar",
            "Parbhani",
            "Pune",
            "Raigad",
            "Ratnagiri",
            "Sangli",
            "Satara",
            "Sindhudurg",
            "Solapur",
            "Thane",
            "Wardha",
            "Washim",
            "Yavatmal"
        )

        var districtInRajasthan: Array<String> = arrayOf(
            "None",
            "Ajmer",
            "Alwar",
            "Banswara",
            "Baran",
            "Barmer",
            "Bharatpur",
            "Bhilwara",
            "Bikaner",
            "Bundi",
            "Chittorgarh",
            "Churu",
            "Dausa",
            "Dholpur",
            "Dungarpur",
            "Hanumangarh",
            "Jaipur",
            "Jaisalmer",
            "Jalore",
            "Jhalawar",
            "Jhunjhunu",
            "Jodhpur",
            "Karauli",
            "Kota",
            "Nagaur",
            "Pali",
            "Pratapgarh",
            "Rajsamand",
            "Sawai Madhopur",
            "Sikar",
            "Sirohi",
            "Sri Ganganagar",
            "Tonk",
            "Udaipur"
        )

        var districtInUttarPradesh: Array<String> = arrayOf(
            "None",
            "Agra",
            "Aligarh",
            "Allahabad",
            "Ambedkar Nagar",
            "Amethi",
            "Amroha",
            "Auraiya",
            "Azamgarh",
            "Baghpat",
            "Bahraich",
            "Ballia",
            "Balrampur",
            "Banda",
            "Barabanki",
            "Bareilly",
            "Basti",
            "Bhadohi",
            "Bijnor",
            "Budaun",
            "Bulandshahr",
            "Chandauli",
            "Chitrakoot",
            "Deoria",
            "Etah",
            "Etawah",
            "Faizabad",
            "Farrukhabad",
            "Fatehpur",
            "Firozabad",
            "Gautam Buddha Nagar",
            "Ghaziabad",
            "Ghazipur",
            "Gonda",
            "Gorakhpur",
            "Hamirpur",
            "Hapur",
            "Hardoi",
            "Hathras",
            "Jalaun",
            "Jaunpur",
            "Jhansi",
            "Kannauj",
            "Kanpur Dehat",
            "Kanpur Nagar",
            "Kanshiram Nagar",
            "Kaushambi",
            "Kushinagar",
            "Lakhimpur - Kheri",
            "Lalitpur",
            "Lucknow",
            "Maharajganj",
            "Mahoba",
            "Mainpuri",
            "Mathura",
            "Mau",
            "Meerut",
            "Mirzapur",
            "Moradabad",
            "Muzaffarnagar",
            "Pilibhit",
            "Pratapgarh",
            "RaeBareli",
            "Rampur",
            "Saharanpur",
            "Sambhal",
            "Sant Kabir Nagar",
            "Shahjahanpur",
            "Shamali",
            "Shravasti",
            "Siddharth Nagar",
            "Sitapur",
            "Sonbhadra",
            "Sultanpur",
            "Unnao",
            "Varanasi"
        )

        var districtInWestBengal: Array<String> = arrayOf(
            "None",
            "Alipurduar",
            "Bankura",
            "Birbhum",
            "Cooch Behar",
            "Dakshin Dinajpur",
            "Darjeeling",
            "Hooghly",
            "Howrah",
            "Jalpaiguri",
            "Jhargram",
            "Kalimpong",
            "Kolkata",
            "Malda",
            "Murshidabad",
            "Nadia",
            "North 24 Parganas",
            "Paschim Medinipur",
            "Paschim Burdwan",
            "Purba Burdwan",
            "Purba Medinipur",
            "Purulia",
            "South 24 Parganas",
            "Uttar Dinajpur"
        )

        var districtInKerala: Array<String> = arrayOf(
            "None",
            "Alappuzha",
            "Ernakulam",
            "Idukki",
            "Kannur",
            "Kasaragod",
            "Kollam",
            "Kottayam",
            "Kozhikode",
            "Malappuram",
            "Palakkad",
            "Pathanamthitta",
            "Thiruvananthapuram",
            "Thrissur",
            "Wayanad"
        )

        var districtInAndhraPradesh: Array<String> = arrayOf(
            "None",
            "Anantapur",
            "Chittoor",
            "East Godavari",
            "Guntur",
            "Krishna",
            "Kurnool",
            "Prakasam",
            "Srikakulam",
            "Sri Potti Sriramulu Nellore",
            "Visakhapatnam",
            "Vizianagaram",
            "West Godavari",
            "Kadapa"
        )

        var emptyDistricts : Array<String> = arrayOf("None")

        var stateArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            states
        )

        spStateSelector.adapter = stateArrayAdapter


        someMap = mapOf(
            "Andhra Pradesh" to districtInAndhraPradesh,
            "Gujarat" to districtInGujarat,
            "Kerala" to districtInKerala,
            "Maharashtra" to districtInMaha,
            "Rajasthan" to districtInRajasthan,
            "Uttar Pradesh" to districtInUttarPradesh,
            "West Bengal" to districtInWestBengal,

            "Chandigarh" to emptyDistricts,
            "Chattisgarh" to emptyDistricts,
            "Hariyana" to emptyDistricts,
            "Himachal Pradesh" to emptyDistricts,
            "Jammu & Kashmir" to emptyDistricts,
            "Jharkhand" to emptyDistricts,
            "Karnataka" to emptyDistricts,
            "Madhya Pradesh" to emptyDistricts,
            "Odisha" to emptyDistricts,
            "Pudu Cherry" to emptyDistricts,
            "Punjab" to emptyDistricts,
            "Tamil Nadu" to emptyDistricts,
            "Telangana" to emptyDistricts,
            "Uttarakhand" to emptyDistricts
        )


        spStateSelector.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    textAPMCWarning.text = "Please Select State and District"
                    //recycleAPMC.visibility = View.GONE
                    textAPMCWarning.visibility = View.VISIBLE
                } else {
                    val districtArrayAdapter = ArrayAdapter(
                        activity!!.applicationContext,
                        android.R.layout.simple_spinner_dropdown_item,

                        someMap!![states[p2]]!!
                    )

                    Spinner1 = p2
                    spDistrictSelector.adapter = districtArrayAdapter
                    districtArrayAdapter.notifyDataSetChanged()
                }
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }


        spDistrictSelector.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    textAPMCWarning.text = "Please Select District"
                    //recycleAPMC.visibility = View.GONE
                    textAPMCWarning.visibility = View.VISIBLE
                } else {
                    textAPMCWarning.visibility = View.GONE
                    if (p2 != 0) {
                        getApmc(someMap!![states!![Spinner1!!]]!![p2])
                    }
                    Spinner2 = p2
                    //progress_apmc.visibility = View.VISIBLE
                    //loadingTextAPMC.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), someMap!![states[Spinner1!!]]!![p2].toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun getApmc(district: String) {

        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                APMC_API.ResultRequest.getSomeData(district)
            }
            if (response.isSuccessful) {
                val apmcRecord = response.body()


                val apmcdata = response.body()
                if (apmcdata != null) {

                    val updatedYear = apmcdata.updated_date.toString().slice(0..3)
                    val updatedMonth = apmcdata.updated_date.toString().slice(5..6)
                    val updatedDate = apmcdata.updated_date.toString().slice(8..9)


                    tvLastUpdateText.text = "$updatedDate/$updatedMonth/$updatedYear"
                    if (apmcdata.records.size == 0) {
                        //progress_apmc.visibility = View.GONE
                        //loadingTextAPMC.visibility = View.GONE
                        textAPMCWarning.visibility = View.VISIBLE
                        recycleAPMC.visibility = View.GONE
                        textAPMCWarning.text = "No records found!"
                    } else {
                        textAPMCWarning.visibility = View.GONE
                        recycleAPMC.visibility = View.VISIBLE
                        Log.d("APMCFrag", apmcdata.records.toString())

                        val totalRecords = apmcdata.records.size
                        var firstMarket = ""
                        if (!apmcdata.records[0].market.isNullOrEmpty()) {
                            firstMarket = apmcdata.records[0].market.toString()
                        }

                        val customRecords = ArrayList<APMCCustomRecords>()

                        val commodityList = mutableListOf<String>()
                        val MinValueList = mutableListOf<String>()
                        val MaxValueList = mutableListOf<String>()
                        commodityList.add(apmcdata.records[0].commodity)
                        MinValueList.add(apmcdata.records[0].min_price)
                        MaxValueList.add(apmcdata.records[0].max_price)

                        var previousRecord = APMCCustomRecords(
                            apmcdata.records[0].state,
                            apmcdata.records[0].district,
                            apmcdata.records[0].market,
                            commodityList,
                            MinValueList,
                            MaxValueList
                        )

                        val ss = apmcdata.records[0].market
                        Log.d("PreREc", previousRecord.toString())


                            var count = 0
                            for (i in 1..totalRecords - 1) {

                                if (apmcdata.records[i].market == previousRecord.market) {
                                    previousRecord.commodity.add(apmcdata.records[i].commodity)
                                    previousRecord.min_price.add(apmcdata.records[i].min_price)
                                    previousRecord.max_price.add(apmcdata.records[i].max_price)
                                    count = 1
                                } else if(apmcdata.records[i].market != previousRecord.market) {
                                    count = 0
                                    customRecords.add(previousRecord)
                                    commodityList.add(apmcdata.records[i].commodity)
                                    MinValueList.add(apmcdata.records[i].min_price)
                                    MaxValueList.add(apmcdata.records[i].max_price)
                                    previousRecord = APMCCustomRecords(
                                        apmcdata.records[i].state,
                                        apmcdata.records[i].district,
                                        apmcdata.records[i].market,
                                        commodityList,
                                        MinValueList,
                                        MaxValueList
                                    )
                                }
                            }
                            if (count == 1) {
                                Log.d("LastRec", "Yes")
                                customRecords.add(previousRecord)
                            }


                        Log.d("New APMC Data", customRecords.toString())
                        Log.d("Old APMC Data", apmcdata.toString())

                        adapter = ApmcAdapter(customRecords)
                        recycleAPMC.adapter = adapter
                        recycleAPMC.layoutManager = LinearLayoutManager(requireContext())
                        //progress_apmc.visibility = View.GONE
                        //loadingTextAPMC.visibility = View.GONE
                        Log.d("bharat222", apmcdata.toString())
                    }

                }
            }


        }

    }

}