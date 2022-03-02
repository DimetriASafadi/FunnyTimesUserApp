package com.example.funnytimesuserapp.SectionService.SectionPayment

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import com.andrewjapar.rangedatepicker.CalendarPicker
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtServiceDatingScreenBinding
import com.jaygoo.widget.RangeSeekBar
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class ServiceDatingScreen : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    lateinit var binding:FtServiceDatingScreenBinding

    var defaultEnterTime: Calendar = Calendar.getInstance()
    var defaultLeaveHour: Calendar = Calendar.getInstance()
    var chosable = "NoOne"

    var itemid = 0
    var booking_type = 0
    var period = "1"
    var start_hour = ""
    var end_hour = ""
    var start_date = ""
    var end_date = ""

    val commonFuncs = CommonFuncs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtServiceDatingScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        itemid = intent.getIntExtra("itemid",0).toString().toInt()
        booking_type = intent.getIntExtra("bookingType",0).toString().toInt()
        Log.e("itemid",booking_type.toString())
        Log.e("booking_type",booking_type.toString())

//        1 range selection only
//        2 single selection with time
//        3 single selection with am or pm

        if (booking_type == 1){
            binding.RangeDatePickerView.setMode(CalendarPicker.SelectionMode.RANGE)
            binding.HTVDatesHolder.visibility = View.GONE
            binding.HTVPeriodsHolder.visibility = View.GONE
            binding.RangeDatePickerView.setOnRangeSelectedListener{ startDate, endDate, startLabel, endLabel ->
                var temp1 = Calendar.getInstance()
                var temp2 = Calendar.getInstance()
                temp1.time = startDate
                temp2.time = endDate
                start_date = commonFuncs.convertMilliToDate(startDate.time)
                end_date = commonFuncs.convertMilliToDate(endDate.time)
                val result = temp2.timeInMillis - temp1.timeInMillis
                var ncounts1 = TimeUnit.MILLISECONDS.toDays(result).toString()
                binding.NightsCount.text = "عدد الليالي : $ncounts1 ليلة"
            }
        }else if (booking_type == 2){
            binding.RangeDatePickerView.setMode(CalendarPicker.SelectionMode.SINGLE)
            binding.HTVDatesHolder.visibility = View.VISIBLE
            binding.HTVPeriodsHolder.visibility = View.GONE
            binding.NightsCount.visibility = View.GONE
            binding.RangeDatePickerView.setOnStartSelectedListener{startDate, label ->
                start_date = commonFuncs.convertMilliToDate(startDate.time)
            }
        }else if (booking_type == 3){
            binding.RangeDatePickerView.setMode(CalendarPicker.SelectionMode.SINGLE)
            binding.HTVDatesHolder.visibility = View.GONE
            binding.HTVPeriodsHolder.visibility = View.VISIBLE
            binding.NightsCount.visibility = View.GONE
            binding.RangeDatePickerView.setOnStartSelectedListener{startDate, label ->
                start_date = commonFuncs.convertMilliToDate(startDate.time)
            }
        }else if(booking_type == 4){
            binding.RangeDatePickerView.setMode(CalendarPicker.SelectionMode.SINGLE)
            binding.HTVDatesHolder.visibility = View.VISIBLE
            binding.HTVPeriodsHolder.visibility = View.GONE
            binding.NightsCount.visibility = View.GONE
            binding.LeavingSection.visibility = View.GONE
            binding.RangeDatePickerView.setOnStartSelectedListener{startDate, label ->
                start_date = commonFuncs.convertMilliToDate(startDate.time)
            }
        }


        binding.MorningPerButton.setOnClickListener {
            binding.MorningPerButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.EveningPerButton.setBackgroundResource(0)
            binding.OrdersTextImg.setTextColor(getColor(R.color.ft_grey_1))
            period = "1"
        }
        binding.EveningPerButton.setOnClickListener {
            binding.EveningPerButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.OrdersTextImg.setTextColor(getColor(R.color.ft_dark_blue))
            binding.MorningPerButton.setBackgroundResource(0)
            binding.BooksTextImg.setTextColor(getColor(R.color.ft_grey_1))
            period = "2"
        }

        binding.PEditEnter.setOnClickListener {
            chosable = "Entering"
            TimePickerDialog(this,this,defaultEnterTime.get(Calendar.HOUR_OF_DAY),defaultEnterTime.get(
                Calendar.MINUTE),false).show()
        }
        binding.PEditLeave.setOnClickListener {
            chosable = "Leaving"
            TimePickerDialog(this,this,defaultEnterTime.get(Calendar.HOUR_OF_DAY),defaultEnterTime.get(
                Calendar.MINUTE),false).show()
        }
        binding.ConfirmDate.setOnClickListener {
            CheckBook_Request()
        }


    }


    fun CheckBook_Request(){
        if (booking_type == 1){
            if (start_date.isNullOrEmpty() || end_date.isNullOrEmpty()){
                Toast.makeText(this,"عليك تحديد تاريخ الدخول أو الخروج",Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (booking_type == 2){
            if (start_date.isNullOrEmpty()){
                Toast.makeText(this,"عليك تحديد تاريخ الدخول",Toast.LENGTH_SHORT).show()
                return
            }
            if (start_hour.isNullOrEmpty() || end_hour.isNullOrEmpty()){
                Toast.makeText(this,"عليك تحديد وقت الدخول أو الخروج",Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (booking_type == 3){
            if (start_date.isNullOrEmpty()){
                Toast.makeText(this,"عليك تحديد تاريخ الدخول",Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (booking_type == 4){
            if (start_date.isNullOrEmpty()){
                Toast.makeText(this,"عليك تحديد تاريخ الذهاب",Toast.LENGTH_SHORT).show()
                return
            }
            if (start_hour.isNullOrEmpty()){
                Toast.makeText(this,"عليك تحديد وقت الذهاب",Toast.LENGTH_SHORT).show()
                return
            }
        }
        commonFuncs.showLoadingDialog(this)

        var url = Constants.APIMain + "api/book/check"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val status = jsonobj.getJSONObject("status")
                    if (status.getBoolean("status")){
                        val intent = Intent(this,PaymentScreen::class.java)
                        if (booking_type == 1){
                            intent.putExtra("itemid",itemid)
                            intent.putExtra("booking_type",booking_type)
                            intent.putExtra("start_date",start_date)
                            intent.putExtra("end_date",end_date)
                            startActivity(intent)
                        }else if (booking_type == 2){
                            intent.putExtra("itemid",itemid)
                            intent.putExtra("booking_type",booking_type)
                            intent.putExtra("start_date",start_date)
                            intent.putExtra("start_hour",start_hour)
                            intent.putExtra("end_hour",end_hour)
                            startActivity(intent)
                        }else if (booking_type == 3){
                            intent.putExtra("itemid",itemid)
                            intent.putExtra("booking_type",booking_type)
                            intent.putExtra("start_date",start_date)
                            intent.putExtra("period",period)
                            startActivity(intent)
                        }else if (booking_type == 4){
                            intent.putExtra("itemid",itemid)
                            intent.putExtra("booking_type",booking_type)
                            intent.putExtra("start_date",start_date)
                            intent.putExtra("start_hour",start_hour)
                            startActivity(intent)
                        }

                    }else{
                        commonFuncs.showDefaultDialog(this,"خطأ في الاتصال","حصل خطأ ما")
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

                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String,String>()
                    if (booking_type == 1){
                        params["product_id"] = itemid.toString()
                        params["type"] = booking_type.toString()
                        params["start_date"] = start_date
                        params["end_date"] = end_date
                    }else if (booking_type == 2){
                        params["product_id"] = itemid.toString()
                        params["type"] = booking_type.toString()
                        params["start_date"] = start_date
                        params["start_hour"] = start_hour
                        params["end_hour"] = end_hour
                    }else if (booking_type == 3){
                        params["product_id"] = itemid.toString()
                        params["type"] = booking_type.toString()
                        params["start_date"] = start_date
                        params["period"] = period
                    }else if (booking_type == 4){
                        params["product_id"] = itemid.toString()
                        params["type"] = booking_type.toString()
                        params["start_date"] = start_date
                        params["start_hour"] = start_hour
                    }
                    return params
                }
                override fun getHeaders(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    if (commonFuncs.IsInSP(this@ServiceDatingScreen, Constants.KeyUserToken)){
                        map["Authorization"] = "Bearer "+commonFuncs.GetFromSP(this@ServiceDatingScreen, Constants.KeyUserToken)
                    }
                    return map
                }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
        }
    }




    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        CheckSelectedTime(p1,p2)
        Log.e("ReturnedHour", "$p1,$p2")
    }

    fun CheckSelectedTime(theHour:Int,theMinute:Int){
        var readyhour = theHour.toString()
        var readyminute = theMinute.toString()
        Log.e("ReturnedHour",readyhour+","+readyminute)
        var readyampm = "صباحاً"
        if (theMinute.toString().length == 1){
            readyminute = "0$theMinute"
        }

        if (theHour > 12){
            readyhour = (theHour - 12).toString()
            readyampm = "مساءاً"
        }
        if (theHour < 12){
            readyhour = theHour.toString()
            readyampm = "صباحاً"
        }
        if (theHour == 0){
            readyhour = "12"
            readyampm = "صباحاً"
        }
        if (theHour == 12){
            readyhour = "12"
            readyampm = "مساءاً"
        }
        if (readyhour.length == 1){
            Log.e("wtfHour",readyhour.toString())
            readyhour = "0"+readyhour
        }


        if (chosable.equals("Entering")){
            binding.PEditEnterTime.text = "$readyhour:$readyminute $readyampm"
            defaultEnterTime.set(Calendar.MINUTE,theMinute)
            defaultEnterTime.set(Calendar.HOUR_OF_DAY,theHour)
            start_hour = "$theHour:00:00"
        }else{
            binding.PEditLeaveTime.text = "$readyhour:$readyminute $readyampm"
            defaultLeaveHour.set(Calendar.MINUTE,theMinute)
            defaultLeaveHour.set(Calendar.HOUR_OF_DAY,theHour)
            end_hour = "$theHour:00:00"
        }

    }
}