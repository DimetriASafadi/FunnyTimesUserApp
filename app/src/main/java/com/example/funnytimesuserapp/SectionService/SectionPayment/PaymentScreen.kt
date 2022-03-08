package com.example.funnytimesuserapp.SectionService.SectionPayment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.APIMain
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.Models.FTBank
import com.example.funnytimesuserapp.Models.FTClinicService
import com.example.funnytimesuserapp.RecViews.BankRecView
import com.example.funnytimesuserapp.databinding.FtScreenPaymentBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset


class PaymentScreen : AppCompatActivity() {

    var chosenpic = 0
    lateinit var chosenpicuri: Uri



    val commonFuncs = CommonFuncs()
    lateinit var binding: FtScreenPaymentBinding
    lateinit var bankRecView: BankRecView
    val ftbanks = ArrayList<FTBank>()
    val client = OkHttpClient()



    var itemid = 0
    var booking_type = 0
    var period = "1"
    var payment_gateway = "1"
    var price = "0"
    var total = ""
    var start_hour = ""
    var end_hour = ""
    var start_date = ""
    var end_date = ""
    var nights_count = ""
    val clinicServices = ArrayList<FTClinicService>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtScreenPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemid = intent.getIntExtra("itemid",0)
        booking_type = intent.getIntExtra("booking_type",0)
        nights_count = intent.getStringExtra("nights_count").toString()

        if (booking_type == 1){
            start_date = intent.getStringExtra("start_date").toString()
            end_date = intent.getStringExtra("end_date").toString()
            total = intent.getStringExtra("total").toString()
        }else if (booking_type == 2){
            start_date = intent.getStringExtra("start_date").toString()
            start_hour = intent.getStringExtra("start_hour").toString()
            end_hour = intent.getStringExtra("end_hour").toString()
            price = intent.getStringExtra("price").toString()
        }else if (booking_type == 3){
            start_date = intent.getStringExtra("start_date").toString()
            period = intent.getStringExtra("period").toString()
            price = intent.getStringExtra("price").toString()
        }else if (booking_type == 4){
            start_date = intent.getStringExtra("start_date").toString()
            start_hour = intent.getStringExtra("start_hour").toString()
            price = intent.getStringExtra("price").toString()
            val services = intent.getSerializableExtra("services") as ArrayList<FTClinicService>
            clinicServices.addAll(services)
        }



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

        banks_Request()
    }


    fun pickimage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            chosenpicuri = uri
            binding.BankTransPhoto.setImageURI(uri)
            chosenpic = 1
            Log.e("ImageUri",chosenpicuri.toString())
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }



    fun Bill_Book_Request(){
        try {
            commonFuncs.showLoadingDialog(this)
            val multipartBody = MultipartBody.Builder()
            multipartBody.setType(MultipartBody.FORM)
            multipartBody.addFormDataPart("product_id", itemid.toString())
            multipartBody.addFormDataPart("type", booking_type.toString())
            multipartBody.addFormDataPart("nights_count", nights_count)
            multipartBody.addFormDataPart("payment_gateway", payment_gateway)

            if (booking_type == 1){
                multipartBody.addFormDataPart("start_date", start_date)
                multipartBody.addFormDataPart("end_date", end_date)
                multipartBody.addFormDataPart("total", total)

            }else if (booking_type == 2){
                multipartBody.addFormDataPart("start_date", start_date)
                multipartBody.addFormDataPart("start_hour", start_hour)
                multipartBody.addFormDataPart("end_hour", end_hour)
                multipartBody.addFormDataPart("nights_count", nights_count)
                multipartBody.addFormDataPart("total", price)
            }else if (booking_type == 3){
                multipartBody.addFormDataPart("start_date", start_date)
                multipartBody.addFormDataPart("period", period)
                multipartBody.addFormDataPart("nights_count", nights_count)
                multipartBody.addFormDataPart("total", price)
            }else if (booking_type == 4){
                multipartBody.addFormDataPart("start_date", start_date)
                multipartBody.addFormDataPart("start_hour", start_hour)
                multipartBody.addFormDataPart("nights_count", nights_count)
                multipartBody.addFormDataPart("total", price)
                for (i in 0 until clinicServices.size) {
                    multipartBody.addFormDataPart("services[]",clinicServices[i].ServiceId.toString())
                }
            }
                val file1 = File(chosenpicuri.path)
                val fileRequestBody = file1.asRequestBody("image/jpg".toMediaType())
                val imagename = System.currentTimeMillis().toString()
                multipartBody.addFormDataPart("bill_image", imagename, fileRequestBody)
            val requestBody: RequestBody = multipartBody.build()
            val request: okhttp3.Request = okhttp3.Request.Builder()
                .addHeader("Authorization", "Bearer "+commonFuncs.GetFromSP(this, Constants.KeyUserToken))
                .url(APIMain+"api/book/post")
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
                        commonFuncs.showDefaultDialog(this@PaymentScreen,"فشل في العملية","حصل خطأ ما أثناء عملية الدفع , تأكد من اتصالك بالانترنت أو حاول مرة أخرى")
                    }
                }
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: okhttp3.Response) {
                    runOnUiThread {
                        Log.e("onResponse",response.message.toString())
                        commonFuncs.hideLoadingDialog()
//                        commonFuncs.showDefaultDialog(this@PaymentScreen,"نجاح العملية","تم إرسال طلبك بنجاح")
                        commonFuncs.WriteOnSP(this@PaymentScreen,"PurchasedFrom","Service")
                        val intent = Intent(this@PaymentScreen, MainMenu::class.java)
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
                    if (booking_type == 1){
                        binding.ConfirmPayment.text = "ادفع $total ر.س"
                    }else if (booking_type == 2){
                        binding.ConfirmPayment.text = "ادفع $price ر.س"
                    }else if (booking_type == 3){
                        binding.ConfirmPayment.text = "ادفع $price ر.س"
                    }else if (booking_type == 4){
                        binding.ConfirmPayment.text = "ادفع $price ر.س"
                    }
                    binding.ConfirmPayment.setOnClickListener {
                        if (chosenpic == 0){
                            Toast.makeText(this, "يجب عليك اختيار صورة للفاتورة", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        if (bankRecView.getSelectedBank() == 999){
                            Toast.makeText(this, "يجب عليك اختيار بنك", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        Bill_Book_Request()
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
                    if (commonFuncs.IsInSP(this@PaymentScreen, Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(this@PaymentScreen, Constants.KeyUserToken)
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


}