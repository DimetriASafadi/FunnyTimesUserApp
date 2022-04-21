package com.example.funnytimesuserapp.MainMenuSection.UserSection

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.Interfaces.OnBookClick
import com.example.funnytimesuserapp.Interfaces.OnOrderClick
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.Models.*
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.RecViews.BookServicesRecView
import com.example.funnytimesuserapp.RecViews.BooksRecView
import com.example.funnytimesuserapp.RecViews.OrderItemsRecView
import com.example.funnytimesuserapp.RecViews.OrdersRecView
import com.example.funnytimesuserapp.databinding.FtDialogObBookDetailsBinding
import com.example.funnytimesuserapp.databinding.FtDialogObOrderDetailsBinding
import com.example.funnytimesuserapp.databinding.FtDialogProfileEditingBinding
import com.example.funnytimesuserapp.databinding.FtMainUserBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.GsonBuilder
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset

class FragUser : Fragment(), OnBookClick, OnOrderClick {
    private var _binding: FtMainUserBinding? = null
    private var ProDialog: FtDialogProfileEditingBinding? = null
    private val binding get() = _binding!!


    var chosenpic = 0
    lateinit var chosenpicuri: Uri
    val client = OkHttpClient()


    val commonFuncs = CommonFuncs()
    val ftBooks = ArrayList<FTBook>()
    val ftOrders = ArrayList<FTOrder>()

    lateinit var booksRecView: BooksRecView
    lateinit var ordersRecView: OrdersRecView

    var bookDialog: Dialog? = null
    var orderDialog: Dialog? = null
    var profileDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainUserBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.BooksButton.setOnClickListener {
            binding.BooksButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersButton.setBackgroundResource(0)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.VISIBLE
            binding.SwipeOrders.visibility = View.INVISIBLE
        }
        binding.OrdersButton.setOnClickListener {
            binding.OrdersButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksButton.setBackgroundResource(0)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.INVISIBLE
            binding.SwipeOrders.visibility = View.VISIBLE
        }

        binding.SwipeBooks.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener { direction ->
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                get_Books_Request(requireActivity())
            }
        })
        binding.SwipeOrders.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener { direction ->
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                get_Orders_Request(requireActivity())
            }
        })


        if (commonFuncs.GetFromSP(requireContext(),"PurchasedFrom") == "Order"){
            commonFuncs.DeleteFromSP(requireContext(),"PurchasedFrom")
            binding.OrdersButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksButton.setBackgroundResource(0)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.INVISIBLE
            binding.SwipeOrders.visibility = View.VISIBLE


        }else if (commonFuncs.GetFromSP(requireContext(),"PurchasedFrom") == "Service"){
            commonFuncs.DeleteFromSP(requireContext(),"PurchasedFrom")
            binding.BooksButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.BooksTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersButton.setBackgroundResource(0)
            binding.OrdersTextImg.setTextColor(resources.getColor(R.color.ft_grey_1,null))
            binding.OrdersTextImg.compoundDrawableTintList = ColorStateList.valueOf(resources.getColor(R.color.ft_grey_1,null))
            binding.SwipeBooks.visibility = View.VISIBLE
            binding.SwipeOrders.visibility = View.INVISIBLE

        }

        booksRecView = BooksRecView(ftBooks,requireContext(),this)
        ordersRecView = OrdersRecView(ftOrders,requireContext(),this)
        binding.OrdersRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.BooksRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)

        binding.OrdersRecycler.adapter = ordersRecView
        binding.BooksRecycler.adapter = booksRecView


        if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
            get_profile_Request(requireActivity())
            get_Books_Request(requireActivity())
            get_Orders_Request(requireActivity())
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (bookDialog != null){
            if (bookDialog!!.isShowing){
                bookDialog!!.dismiss()
            }
        }
        if (orderDialog != null){
            if (orderDialog!!.isShowing){
                orderDialog!!.dismiss()
            }
        }
        if (profileDialog != null){
            if (profileDialog!!.isShowing){
                profileDialog!!.dismiss()
            }
        }
        _binding = null
    }

    fun get_profile_Request(activity: Activity){
        val url = Constants.APIMain + "api/auth/user/"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val img = data.getString("img").toString()
                    Glide.with(requireActivity())
                        .load(img)
                        .centerCrop()
                        .placeholder(R.drawable.ft_broken_image)
                        .into(binding.UserImage)
                    binding.UserName.text = data.getString("name").toString()
                    binding.UserEmail.text = data.getString("email").toString()
                    binding.UserPhone.text = data.getString("phone").toString()
                    SetUpProfileEditDialog(data)
                    binding.UserEditProfile.setOnClickListener {
                        profileDialog?.show()
                    }

                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(activity, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(activity)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
        }
    }
    fun get_Books_Request(activity: Activity){
        val url = Constants.APIMain + "api/book/get"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONArray("data")
                    val gson = GsonBuilder().create()
                    ftBooks.clear()
                    ftBooks.addAll(gson.fromJson(data.toString(),Array<FTBook>::class.java).toList())
                    Log.e("ftbooks", ftBooks.toString())
                    booksRecView.notifyDataSetChanged()
                    binding.SwipeBooks.isRefreshing = false
                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    binding.SwipeBooks.isRefreshing = false
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(activity, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(activity)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
        }
    }
    fun get_Orders_Request(activity: Activity){
        val url = Constants.APIMain + "api/order/"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.GET, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONArray("data")
                    val gson = GsonBuilder().create()
                    ftOrders.clear()
                    ftOrders.addAll(gson.fromJson(data.toString(),Array<FTOrder>::class.java).toList())
                    Log.e("ftOrders", ftOrders.toString())
                    ordersRecView.notifyDataSetChanged()
                    binding.SwipeOrders.isRefreshing = false
                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(activity,"خطأ في الاتصال","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    binding.SwipeOrders.isRefreshing = false
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(activity, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(activity)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
        }
    }
    override fun OnBookClickListener(ftbook: FTBook) {
        ShowBookDialog(ftbook)
    }
    override fun OnOrderClickListener(ftOrder: FTOrder) {
        ShowOrderDialog(ftOrder)
    }
    fun ShowBookDialog(ftbook: FTBook){
        bookDialog = Dialog(requireContext())
        bookDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bookDialog?.setCancelable(true)
        val dialogBinding = FtDialogObBookDetailsBinding.inflate(layoutInflater)
        val view = dialogBinding.root
        bookDialog?.setContentView(view)
        dialogBinding.BookId.text = "* الحجز رقم "+ftbook.BookId+" *"
        dialogBinding.BookCreatedAt.text = ftbook.BookCreatedAt
        dialogBinding.BookPropName.text = ftbook.BookName
        dialogBinding.BookStartDate.text = ftbook.BookStartDate
        dialogBinding.BookEndDate.text = ftbook.BookEndDate
        dialogBinding.BookStartHour.text = ftbook.BookStartHour
        dialogBinding.BookEndHour.text = ftbook.BookEndHour
        dialogBinding.BookTotal.text = ftbook.BookTotal.toString()
        dialogBinding.BookPeriod.text = ftbook.BookPeriod.toString()
        dialogBinding.BookNightsCount.text = ftbook.BookNightCount.toString()
        dialogBinding.BookPaymentMethod.text = ftbook.BookPayment.toString()
        if (ftbook.BookDetails != null){
            dialogBinding.BookVendor.text = ftbook.BookDetails!!.BookVendorName.toString()
            dialogBinding.BookPropAddress.text = ftbook.BookDetails!!.BookPropAddress.toString()
            val bookservices = ArrayList<FTClinicService>()
            bookservices.clear()
            if (!ftbook.BookDetails!!.BookPropServices.isNullOrEmpty()){
                bookservices.addAll(ftbook.BookDetails!!.BookPropServices!!)
            }
            if (bookservices.size == 0){
                dialogBinding.BookServicesSection.visibility = View.GONE
            }else{
                dialogBinding.BookServicesSection.visibility = View.VISIBLE
                val bookServicesRecView = BookServicesRecView(bookservices,requireContext())
                dialogBinding.BookServicesRecycler.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
                dialogBinding.BookServicesRecycler.adapter = bookServicesRecView
            }
        }


        val window: Window = bookDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(requireActivity().resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        bookDialog?.show()

    }
    fun ShowOrderDialog(ftOrder:FTOrder){
        orderDialog = Dialog(requireContext())
        orderDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        orderDialog?.setCancelable(true)
        val dialogBinding = FtDialogObOrderDetailsBinding.inflate(layoutInflater)
        val view = dialogBinding.root
        orderDialog?.setContentView(view)
        dialogBinding.OrderId.text = "* طلب رقم "+ftOrder.OrderId+" *"
        dialogBinding.OrderCreatedAt.text = ftOrder.OrderCreatedAt
        dialogBinding.OrderTotal.text = ftOrder.OrderTotal.toString()
        dialogBinding.OrderPaymentStatus.text = ftOrder.OrderPaymentStatus.toString()
        dialogBinding.OrderVendorName.text = ftOrder.OrderVendor.toString()
        dialogBinding.OrderStatus.text = ftOrder.OrderStatus.toString()
        dialogBinding.OrderPaymentMethod.text = ftOrder.OrderPayGateway.toString()
        val orderitems = ArrayList<FTOrderItem>()
        orderitems.clear()
        val OrderItemsRecView = OrderItemsRecView(ftOrder.OrderItems!!,requireContext())
        dialogBinding.OrderItemsRecycler.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
        dialogBinding.OrderItemsRecycler.adapter = OrderItemsRecView

        val window: Window = orderDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(requireActivity().resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        orderDialog?.show()
    }
    fun SetUpProfileEditDialog(data:JSONObject){
        profileDialog = Dialog(requireContext())
        profileDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        profileDialog?.setCancelable(true)
        ProDialog = FtDialogProfileEditingBinding.inflate(layoutInflater)
        val view = ProDialog!!.root
        profileDialog?.setContentView(view)
        ProDialog!!.ProfileName.setText(data.getString("name").toString())
        ProDialog!!.ProfileEmail.setText(data.getString("email").toString())
        val img = data.getString("img").toString()
        Glide.with(requireActivity())
            .load(img)
            .centerCrop()
            .placeholder(R.drawable.ft_broken_image)
            .into(ProDialog!!.ProfileImage)
        ProDialog!!.ProEditImage.setOnClickListener {
            pickimage()
        }
        ProDialog!!.ProfileSave.setOnClickListener {
            val name = ProDialog!!.ProfileName.text.toString()
            val email = ProDialog!!.ProfileEmail.text.toString()

            if (chosenpic == 0){
                Toast.makeText(requireContext(), "يجب عليك اختيار صورة للفاتورة", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (name.isNullOrEmpty()){
                ProDialog!!.ProfileName.error = "لا يمكن ترك الحقل فارغ"
                ProDialog!!.ProfileName.requestFocus()
                return@setOnClickListener
            }
            if (email.isNullOrEmpty()){
                ProDialog!!.ProfileEmail.error = "لا يمكن ترك الحقل فارغ"
                ProDialog!!.ProfileEmail.requestFocus()
                return@setOnClickListener
            }
            Update_Profile_Request(name,email)

        }

        val window: Window = profileDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(requireActivity().resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
    fun pickimage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .start()
    }

    fun Update_Profile_Request(name:String,email:String){
        try {
            commonFuncs.showLoadingDialog(requireActivity())
            val multipartBody = MultipartBody.Builder()
            multipartBody.setType(MultipartBody.FORM)
            multipartBody.addFormDataPart("name", name.toString())
            multipartBody.addFormDataPart("email", email.toString())

            val file1 = File(chosenpicuri.path)
            val fileRequestBody = file1.asRequestBody("image/jpg".toMediaType())
            val imagename = System.currentTimeMillis().toString()
            multipartBody.addFormDataPart("img", imagename, fileRequestBody)
            val requestBody: RequestBody = multipartBody.build()
            val request: okhttp3.Request = okhttp3.Request.Builder()
                .addHeader("Authorization", "Bearer "+commonFuncs.GetFromSP(requireContext(), Constants.KeyUserToken))
                .url(Constants.APIMain +"api/auth/user/update")
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object : Callback {
                @Throws(IOException::class)
                override fun onFailure(call: Call, e: java.io.IOException) {
                    requireActivity().runOnUiThread {
                        Log.e("onFailure","onFailure")
                        Log.e("onFailure",call.toString())
                        Log.e("onFailure",e.message.toString())
                        Log.e("onFailure",e.toString())
                        commonFuncs.hideLoadingDialog()
                        commonFuncs.showDefaultDialog(requireContext(),"فشل في العملية","حصل خطأ ما أثناء العملية , تأكد من اتصالك بالانترنت أو حاول مرة أخرى")
                    }
                }
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: okhttp3.Response) {
                    requireActivity().runOnUiThread {
                        Log.e("onResponse",response.message.toString())
                        Log.e("onResponseCode",response.code.toString())
                        if (response.code.toString() == "200"){
                            commonFuncs.hideLoadingDialog()
                            binding.UserName.text = name
                            binding.UserEmail.text = email
                            binding.UserImage.setImageURI(chosenpicuri)
                            Toast.makeText(requireContext(), "تم التحديث بنجاح", Toast.LENGTH_SHORT).show()
                            profileDialog!!.dismiss()
                        }else{
                            commonFuncs.showDefaultDialog(requireContext(),"فشل في العملية","حصل خطأ ما أثناء العملية , تأكد من اتصالك بالانترنت أو حاول مرة أخرى")
                        }
                    }
                }
            })
        } catch (e: IOException) {
            Log.e("TryCatchFinal",e.message.toString()+"A7a")
            e.printStackTrace()
            commonFuncs.hideLoadingDialog()
            commonFuncs.showDefaultDialog(requireContext(),"خطأ في الاتصال","حصل خطأ ما")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            chosenpicuri = uri
            ProDialog!!.ProfileImage.setImageURI(uri)
            chosenpic = 1
            Log.e("ImageUri",chosenpicuri.toString())
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "تم إلغاء العملية", Toast.LENGTH_SHORT).show()
        }
    }



}