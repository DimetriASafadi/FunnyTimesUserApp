package com.example.funnytimesuserapp.CommonSection

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
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
import android.widget.Toast
import com.example.funnytimesuserapp.R
import java.util.*

class CommonFuncs {
    val SPName:String = "TakeedSharedPreferences"
    var loadingDia: Dialog? = null
    var codePhone: Dialog? = null

    @Suppress("DEPRECATION")
    fun setLocale2(context: Activity,langua:String) {
        WriteOnSP(context,"AppLang",langua)
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

    fun showDialog(activity: Activity) {
        loadingDia = Dialog(activity)
        loadingDia?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDia?.setCancelable(false)
        loadingDia?.setContentView(R.layout.ft_loading_dialog)
        val window: Window = loadingDia?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
            .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        loadingDia?.show()
    }

    fun hideDialog(){
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

    fun showPhoneDoneDialog(activity: Activity) {
        codePhone = Dialog(activity)
        codePhone?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        codePhone?.setCancelable(false)
        codePhone?.setContentView(R.layout.ft_phone_added_successfully_dialog)
        val window: Window = codePhone?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(activity.resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        codePhone?.show()
    }

}