package com.example.funnytimesuserapp.SectionCart.SectionPayment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.LOCATION_REQUEST_CODE
import com.example.funnytimesuserapp.CommonSection.Constants.REQUEST_CODE_CHECK_SETTINGS
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.Models.FTBank
import com.example.funnytimesuserapp.Models.FTInCart
import com.example.funnytimesuserapp.Models.FTProAttrContainer
import com.example.funnytimesuserapp.Models.FTProAttribute
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.BankRecView
import com.example.funnytimesuserapp.SectionItems.ItemsFuncs
import com.example.funnytimesuserapp.databinding.FtScreenCartPaymentBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CartPayment : AppCompatActivity() {

    lateinit var binding: FtScreenCartPaymentBinding
    lateinit var bankRecView: BankRecView
    val ftbanks = ArrayList<FTBank>()

    lateinit var fusedLocationClient: FusedLocationProviderClient
    val commonFuncs = CommonFuncs()
    val itemFuncs = ItemsFuncs()
    val client = OkHttpClient()
    val ItemsArr = ArrayList<FTInCart>()


    var payment_gateway = "1"
    var vendor_id = 0


    var chosenpic = 0
    lateinit var chosenpicuri: Uri

    var wantedLat = ""
    var wantedLng = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenCartPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener {
            finish()
        }

        bankRecView = BankRecView(ftbanks,this)
        binding.BanksRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        binding.BanksRecycler.adapter = bankRecView

        binding.PickImage.setOnClickListener {
            pickimage()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        banks_Request()

        ItemsArr.addAll(intent.getSerializableExtra("SelectCartItems") as ArrayList<FTInCart>)

        binding.ConfirmPayment.text = "ادفع "+itemFuncs.GetCartTotal(ItemsArr).toString()+" ر.س"

        vendor_id = ItemsArr[0].ItemVendorId
        val ItemsTotal = intent.getDoubleExtra("SelectCartItemsTotal",0.0)
        Log.e("ItemsArr",ItemsArr.toString())
        Log.e("ItemsTotal",ItemsTotal.toString())

    }

    fun Bill_Order_Request(){
        try {
            val multipartBody = MultipartBody.Builder()
            multipartBody.setType(MultipartBody.FORM)
            multipartBody.addFormDataPart("vendor_id", vendor_id.toString())
            multipartBody.addFormDataPart("payment_gateway", payment_gateway)
            multipartBody.addFormDataPart("lat", wantedLat)
            multipartBody.addFormDataPart("lng", wantedLng)
            multipartBody.addFormDataPart("payment_gateway", payment_gateway)

            for (i in 0 until ItemsArr.size)
            {
                multipartBody.addFormDataPart("items[$i][product_id]", ItemsArr[i].ItemId.toString())
                multipartBody.addFormDataPart("items[$i][price]", ItemsArr[i].ItemId.toString())
                multipartBody.addFormDataPart("items[$i][qty]", ItemsArr[i].ItemQuantity.toString())
                multipartBody.addFormDataPart("items[$i][total]", ( ItemsArr[i].ItemPrice * ItemsArr[i].ItemQuantity!!).toString())
                for (a in 0 until ItemsArr[i].ItemAttributes.size)
                {
                    multipartBody.addFormDataPart("items[$i][attrabiutes][$a][attrabiute_id]", ItemsArr[i].ItemAttributes[a].AttrTypeId.toString())
                    multipartBody.addFormDataPart("items[$i][attrabiutes][$a][attrabiute_value_id]", ItemsArr[i].ItemAttributes[a].AttrId.toString())
                }
            }


            val file1 = File(chosenpicuri.path)
            val fileRequestBody = file1.asRequestBody("image/jpg".toMediaType())
            val imagename = System.currentTimeMillis().toString()
            multipartBody.addFormDataPart("bill_image", imagename, fileRequestBody)
            val requestBody: RequestBody = multipartBody.build()
            val request: okhttp3.Request = okhttp3.Request.Builder()
                .addHeader("Authorization", "Bearer "+commonFuncs.GetFromSP(this, Constants.KeyUserToken))
                .url(Constants.APIMain +"api/order/post")
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object : Callback {
                @Throws(IOException::class)
                override fun onFailure(call: Call, e: java.io.IOException) {
                    runOnUiThread {
                        Log.e("onFailure","onFailure")
                        Log.e("onFailure",call.toString())
                        Log.e("onFailure",e.message.toString())
                        Log.e("onFailure",e.toString())
                        commonFuncs.hideLoadingDialog()
                        commonFuncs.showDefaultDialog(this@CartPayment,"فشل في العملية","حصل خطأ ما أثناء عملية الدفع , تأكد من اتصالك بالانترنت أو حاول مرة أخرى")
                    }
                }
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: okhttp3.Response) {
                    runOnUiThread {
                        Log.e("onResponse",response.message.toString())
                        commonFuncs.hideLoadingDialog()
//                        commonFuncs.showDefaultDialog(this@CartPayment,"نجاح العملية","تم إرسال طلبك بنجاح")
                        commonFuncs.WriteOnSP(this@CartPayment,"PurchasedFrom","Order")
                        val intent = Intent(this@CartPayment, MainMenu::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()

                    }
                }
            })
        } catch (e: IOException) {
            Log.e("TryCatchFinal",e.message.toString()+"A7a")
            e.printStackTrace()
            commonFuncs.hideLoadingDialog()
            commonFuncs.showDefaultDialog(this,"خطأ في الاتصال","حصل خطأ ما")
        }
    }

    fun pickimage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_CHECK_SETTINGS === requestCode) {
            if (RESULT_OK == resultCode) {
                Log.e("REQUEST_CODE_CHECK_SETTINGS","Granted")
                GetLocationTimer()
                //user clicked OK, you can startUpdatingLocation(...);
            } else {
                commonFuncs.hideLoadingDialog()
                commonFuncs.showDefaultDialog(this,"فشل العملية","فشل عملية الحصول على الموقع , لقد رفضت تشغيل خيار الموقع .")
                Log.e("REQUEST_CODE_CHECK_SETTINGS","Denied")
                //user clicked cancel: informUserImportanceOfLocationAndPresentRequestAgain();
            }
        }else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }else if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!
                chosenpicuri = uri
                binding.BankTransPhoto.setImageURI(uri)
                chosenpic = 1
                Log.e("ImageUri",chosenpicuri.toString())
            }
    }

    fun banks_Request(){
        val url = Constants.APIMain + "api/banking"
        commonFuncs.showLoadingDialog(this)
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONArray("data")
                    val gson = GsonBuilder().create()
                    ftbanks.clear()
                    ftbanks.addAll(gson.fromJson(data.toString(),Array<FTBank>::class.java).toList())
                    bankRecView.notifyDataSetChanged()
                    binding.ConfirmPayment.setOnClickListener {
                        if (chosenpic == 0){
                            Toast.makeText(this, "يجب عليك اختيار صورة للفاتورة", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        if (bankRecView.getSelectedBank() == 999){
                            Toast.makeText(this, "يجب عليك اختيار بنك", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        commonFuncs.showLoadingDialog(this)
                        if (EasyPermissions.hasPermissions(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                            enableLocationSettings()
                        }else{
                            EasyPermissions.requestPermissions(
                                this,
                                "لإستخدام هذه الميزة يجب عليك الموافقة على أذونات الموقع وتفعيل خيار الموقع",
                                LOCATION_REQUEST_CODE,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                    commonFuncs.hideLoadingDialog()
                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"خطأ في الاتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"خطأ في الاتصال","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(this@CartPayment, Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(this@CartPayment, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }










    @SuppressLint("MissingPermission")
    private fun enableLocationSettings() {
        val locationRequest: LocationRequest = LocationRequest.create()
            .setInterval(Constants.LOCATION_UPDATE_INTERVAL)
            .setFastestInterval(Constants.LOCATION_UPDATE_FASTEST_INTERVAL)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        LocationServices
            .getSettingsClient(this)
            .checkLocationSettings(builder.build())
            .addOnSuccessListener(this) { response: LocationSettingsResponse? ->
                Log.e("addOnSuccessListener",response.toString())
                GetLocationTimer()
            }
            .addOnFailureListener(this) { ex: Exception? ->
                if (ex is ResolvableApiException) {
                    // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                    try {
                        Log.e("ex",ex.message.toString())
                        ex.startResolutionForResult(this, Constants.REQUEST_CODE_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Log.e("sendEx",sendEx.toString())
                    }
                }
            }
    }


    override fun onRequestPermissionsResult(requestCode:Int,
                                            permissions:Array<String>,
                                            grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Log.e("hasPermissions","Granted")
            enableLocationSettings()
        }else{
            Log.e("hasPermissions","Denied")
            commonFuncs.showDefaultDialog(this,"فشل العملية","فشل عملية الحصول على الموقع , لقد رفضت إعطاء إذن الموقع .")
        }
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    fun GetLocationTimer(){
        Timer().schedule(object : TimerTask() {
            @SuppressLint("MissingPermission")
            override fun run() {
                fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                    if (location != null){
                        wantedLat = location.latitude.toString()
                        wantedLng = location.longitude.toString()
                        Log.e("locationlat",location.latitude.toString())
                        Log.e("locationlng",location.longitude.toString())
                        Bill_Order_Request()
                    }else{
                        GetLocationTimer()
                    }
                }

            }
        }, 2000)
    }

}