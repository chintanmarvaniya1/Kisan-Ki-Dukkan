package com.example.kisankidukaan.screens.fragments

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.kisankidukaan.R
import kotlinx.android.synthetic.main.fragment_crop_prediction.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CropPredictionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_prediction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Getting Items")
        progressDialog.setCancelable(false)
        //progressDialog.show()


        GlobalScope.launch(Dispatchers.Main){
            wvView.webViewClient =object  : WebViewClient(){
                override fun shouldOverrideUrlLoading(view: WebView?, url1: String?): Boolean {
                    view?.loadUrl(url1!!)
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)

                    progressDialog.show()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (progressDialog != null) {
                        progressDialog.dismiss()
                    }
                }
            }

            wvView.getSettings().javaScriptEnabled = true
           // webSettings.setJavaScriptEnabled(true);
            wvView.loadUrl("https://toxicassstic.herokuapp.com/")

        }
    }
}