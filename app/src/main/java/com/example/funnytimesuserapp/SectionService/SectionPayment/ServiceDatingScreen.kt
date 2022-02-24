package com.example.funnytimesuserapp.SectionService.SectionPayment

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import com.andrewjapar.rangedatepicker.CalendarPicker
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.databinding.FtServiceDatingScreenBinding
import java.util.*

class ServiceDatingScreen : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    lateinit var binding:FtServiceDatingScreenBinding

    var defaultEnterTime: Calendar = Calendar.getInstance()
    var defaultLeaveHour: Calendar = Calendar.getInstance()
    var chosable = "NoOne"
    var booking_type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtServiceDatingScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        booking_type = intent.getIntExtra("bookingType",0).toString().toInt()
        Log.e("booking_type",booking_type.toString())

//        1 range selection only
//        2 single selection with time
//        3 single selection with am or pm

        if (booking_type == 1){

        }else if (booking_type == 2){

        }else if (booking_type == 3){

        }


        binding.BooksButton.setOnClickListener {
            binding.BooksButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.BooksTextImg.setTextColor(resources.getColor(R.color.ft_dark_blue,null))
            binding.OrdersButton.setBackgroundResource(0)
            binding.OrdersTextImg.setTextColor(getColor(R.color.ft_grey_1))
        }
        binding.OrdersButton.setOnClickListener {
            binding.OrdersButton.setBackgroundResource(R.drawable.ft_radius_fill)
            binding.OrdersTextImg.setTextColor(getColor(R.color.ft_dark_blue))
            binding.BooksButton.setBackgroundResource(0)
            binding.BooksTextImg.setTextColor(getColor(R.color.ft_grey_1))
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

//        binding.RangeDatePickerView.setMode(CalendarPicker.SelectionMode.RANGE)
//        binding.RangeDatePickerView.setMode(CalendarPicker.SelectionMode.SINGLE)


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
        }else{
            binding.PEditLeaveTime.text = "$readyhour:$readyminute $readyampm"
            defaultLeaveHour.set(Calendar.MINUTE,theMinute)
            defaultLeaveHour.set(Calendar.HOUR_OF_DAY,theHour)
        }

    }
}