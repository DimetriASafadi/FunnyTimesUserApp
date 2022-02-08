package com.example.funnytimesuserapp.CommonSection

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.funnytimesuserapp.AuthSection.SignInScreen
import com.example.funnytimesuserapp.CommonSection.Constants.AppSPName
import com.example.funnytimesuserapp.CommonSection.Constants.KeyAppLanguage
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.R
import com.jaygoo.widget.RangeSeekBar
import java.util.*

class CommonFuncs {
    val SPName:String = AppSPName
    var filterDialog: Dialog? = null
    var loadingDia: Dialog? = null
    var codePhone: Dialog? = null
    var passwordDoneDialog: Dialog? = null

    @Suppress("DEPRECATION")
    fun setLocale2(context: Activity,langua:String) {
        WriteOnSP(context,KeyAppLanguage,langua)
        //Log.e("Lan",session.getLanguage());
        val locale = Locale(langua)
        val config = Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        context.baseContext.resources.updateConfiguration(config,
            context.baseContext.resources.displayMetrics)
    }


    fun GetFromSP(context: Context, key: String):String?{
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SPName,
            Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "NoValue")
    }
    fun GetLongFromSP(context: Context, key: String):Long?{
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SPName,
            Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, 0)
    }
    fun WriteOnSP(context: Context, key: String, value: String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SPName,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }
    fun WriteLongOnSP(context: Context, key: String, value: Long){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SPName,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
        editor.commit()
    }
    fun DeleteFromSP(context: Context, key: String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SPName,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
        editor.commit()
    }
    fun IsInSP(context: Context, key: String):Boolean{
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SPName,
            Context.MODE_PRIVATE)
        return sharedPreferences.contains(key)
    }

    fun showLoadingDialog(activity: Activity) {
        loadingDia = Dialog(activity)
        loadingDia?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDia?.setCancelable(false)
        loadingDia?.setContentView(R.layout.ft_dialog_loading)
        val window: Window = loadingDia?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
            .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        loadingDia?.show()
    }

    fun hideLoadingDialog(){
        if (loadingDia != null){
            if (loadingDia?.isShowing!!){
                loadingDia?.dismiss()
            }
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.e("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.e("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.e("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun showDefaultDialog(context: Context, title: String, messsage: String){
        val builder = AlertDialog.Builder(context)
        if (!title.isNullOrEmpty()){
            builder.setTitle(title)
        }
        builder.setMessage(messsage)
        builder.setNeutralButton(context.getString(R.string.ok)){ dialog, which ->
        }
        builder.show()
    }

//    fun showPhoneDoneDialog(activity: Activity) {
//        codePhone = Dialog(activity)
//        codePhone?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        codePhone?.setCancelable(false)
////        codePhone?.setContentView(R.layout.ft_phone_added_successfully_dialog)
//        codePhone?.setContentView(R.layout.ft_dialog_main_filter)
//        val window: Window = codePhone?.window!!
//        window.setBackgroundDrawable(
//            ColorDrawable(activity.resources
//                .getColor(R.color.tk_dialog_bg, null))
//        )
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        codePhone?.show()
//    }

    fun showFilterDialog(activity: Activity) {
        filterDialog = Dialog(activity)
        filterDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog?.setCancelable(false)
        filterDialog?.setContentView(R.layout.ft_dialog_main_filter)
        val rangeseek = filterDialog?.findViewById<RangeSeekBar>(R.id.PriceRangeBar)
        rangeseek
        val window: Window = filterDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        filterDialog?.show()
    }

    fun showPasswordDoneDialog(activity: Activity) {
        passwordDoneDialog = Dialog(activity)
        passwordDoneDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        passwordDoneDialog?.setCancelable(false)
        passwordDoneDialog?.setContentView(R.layout.ft_dialog_password_reset_done)
        val donebtn = passwordDoneDialog?.findViewById<TextView>(R.id.GoToSignIn)
        donebtn!!.setOnClickListener {
            passwordDoneDialog!!.dismiss()
            showLoadingDialog(activity)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    hideLoadingDialog()
                    val intent = Intent(activity, SignInScreen::class.java).apply {}
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity.startActivity(intent)
                    activity.finish()
                }
            }, 3000)
        }

        val window: Window = passwordDoneDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        passwordDoneDialog?.show()
    }

    fun showCodeDoneDialog(activity: Activity) {
        codePhone = Dialog(activity)
        codePhone?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        codePhone?.setCancelable(false)
        codePhone?.setContentView(R.layout.ft_dialog_phone_added_successfully)
        val donebtn = codePhone?.findViewById<TextView>(R.id.GoToHome)
        donebtn?.setOnClickListener {
            codePhone!!.dismiss()
            showLoadingDialog(activity)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    hideLoadingDialog()
                    val intent = Intent(activity, MainMenu::class.java).apply {}
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity.startActivity(intent)
                    activity.finish()
                }
            }, 3000)
        }

        val window: Window = codePhone?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        codePhone?.show()
    }

}