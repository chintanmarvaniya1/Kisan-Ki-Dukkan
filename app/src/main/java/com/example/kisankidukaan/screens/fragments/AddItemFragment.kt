package com.example.kisankidukaan.screens.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.kisankidukaan.R
import com.example.kisankidukaan.ml.ModelUnquant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.fragment_apmc.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.*


class AddItemFragment : Fragment() {

    var url1: String? = null


    lateinit var type :String

    lateinit var bitmap: Bitmap
    lateinit var image: Bitmap


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
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        ivCrop1.setImageURI(uri)




        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Getting Images")
        progressDialog.setCancelable(false)
        progressDialog.show()

        bitmap = MediaStore.Images.Media.getBitmap(this.requireActivity().contentResolver, uri)
        image = Bitmap.createScaledBitmap(bitmap, 224, 224, false);

        val model = ModelUnquant.newInstance(requireContext())


        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        // get 1D array of 224 * 224 pixels in image

        // get 1D array of 224 * 224 pixels in image
        val intValues = IntArray(224 * 224)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.

        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
        var pixel = 0
        for (i in 0 until 224) {
            for (j in 0 until 224) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }


        inputFeature0.loadBuffer(byteBuffer)


        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val res = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f
        for (i in 0 until res.size) {
            if (res.get(i) > maxConfidence) {
                maxConfidence = res.get(i)
                maxPos = i
            }
        }

        val classes = arrayOf("Watermelon" ,"Turnip","Tomato","SweetPotato","SweetCorn","Spinach","Raddish","Potato","Pomegranate","Pineaple","Peas","Pear","Paprika","Orange","Onion","Mango","lettuce","lemon","Kiwi","Jalepeno","Grapes","Ginger","Gralic","Eggplant","Cucumber","Corn","ChilliPepper","Cauliflower","Carrot","Capsicum","Cabbage","Bell Pepper","BeetRoot","Banana","Apple","SoyaBean")
        val text = classes[maxPos]

        if (Math.ceil((res[maxPos]*100).toDouble()).toInt() == 100){
            var str = "${classes[0]}  ${res[0]*100} \n"
            Toast.makeText(requireContext(),"${Math.ceil((res[maxPos]*100).toDouble())} ${classes[maxPos]}",Toast.LENGTH_LONG).show()
            /*for(i in 1 until res.size){
                str = str + "${classes[i]}  ${res[i]*100} \n"
            }*/
            //str = str + "${classes[maxPos]}  ${res[maxPos]*100} \n"
            ///textView.setText("${Math.ceil((res[maxPos]*100).toDouble())} ${classes[maxPos]}".toString())





            val firebaseStorage = FirebaseStorage.getInstance()

            val dFormat = SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
            val now = Date()
            val fileName = dFormat.format(now)


            val storageRef1 = firebaseStorage.reference.child("crop/${mAuth.uid}/${fileName}--1")
            val uploadTask1 = storageRef1.putFile(uri!!)
                .addOnSuccessListener {

                    storageRef1.downloadUrl.addOnSuccessListener {
                        url1 = it.toString()
                        if (progressDialog.isShowing) progressDialog.dismiss()
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                }


        }else{
            Toast.makeText(requireContext(),"Invalid Image",Toast.LENGTH_LONG).show()
            if (progressDialog.isShowing) progressDialog.dismiss()
            //textView.setText("Image Not detect")
        }


        model.close()


        //Toast.makeText(requireContext(),uri.toString(),Toast.LENGTH_LONG).show()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val crops: Array<String> = arrayOf(
            "None","Vegetable","Fruits","Cash Crop","Food Crop","Plantation Crops"
        )

        val typeAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,crops)
        spTypeSelector.adapter = typeAdapter

        spTypeSelector.onItemSelectedListener = object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0)
                    type = "None"
                else
                    type = crops[p2].toString()
                    //Toast.makeText(requireContext(),crops[p2],Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }



        btnAddImage.setOnClickListener {

            getImage.launch("image/*")


        }


        btnLogin.setOnClickListener {


            val mDataBaseRef = Firebase.firestore
            if (etTitle.text != null && etDescription.text != null && etQuantity.text != null && etPrice.text != null) {


                val title = etTitle.text.toString().trim()
                val desc = etDescription.text.toString().trim()
                val quantity = etQuantity.text.toString().trim()
                val price = etPrice.text.toString().trim()

                val img1: String = url1.toString()

                try {
                    val items = HashMap<String, Any>()
                    items.put("userId", mAuth.uid!!)
                    items.put("title", title)
                    items.put("description", desc)
                    items.put("quantity", quantity)
                    items.put("price", price)
                    items.put("image1", img1)
                    items.put("availability", true)
                    items.put("type", type)


                    mDataBaseRef.collection("crop").add(items).addOnCompleteListener {
                        //.document("mAuth.uid!!").set(items)
                            Toast.makeText(requireContext(), "Data Added ", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                        }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                }
            }



        }


    }


}