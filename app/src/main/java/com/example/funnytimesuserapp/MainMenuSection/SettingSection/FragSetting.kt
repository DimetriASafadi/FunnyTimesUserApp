package com.example.funnytimesuserapp.MainMenuSection.SettingSection

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.AuthSection.SignInScreen
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.R
import com.example.funnytimesuserapp.SplashScreen
import com.example.funnytimesuserapp.databinding.FtDialogResetPasswordBinding
import com.example.funnytimesuserapp.databinding.FtMainSettingBinding
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.*

class FragSetting : Fragment() {
    private var _binding: FtMainSettingBinding? = null
    private val binding get() = _binding!!

    var passwordDialog:Dialog? = null

    val commonFuncs = CommonFuncs()

    lateinit var mGoogleSignInClient:GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FtMainSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("708944007620-ucphq6tnkafur4vk5p8vcbumaoa7pos6.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)) {
            binding.SettingChangePassword.visibility = View.GONE
            binding.SignInOut.text = "تسجيل خروج"
            binding.SignInOutIcon.setImageResource(R.drawable.ft_setting_signin_icon)
            binding.SignInOutIcon.scaleX*-1
            binding.SettingSignOut.setOnClickListener {
                mGoogleSignInClient.signOut()
                commonFuncs.DeleteFromSP(requireContext(),KeyUserToken)
                LoginManager.getInstance().logOut();
                val intent = Intent(requireContext(),SplashScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }else{
            binding.SettingChangePassword.visibility = View.VISIBLE
            binding.SignInOut.text = "تسجيل دخول"
            binding.SignInOutIcon.setImageResource(R.drawable.ft_setting_signout_icon)
            binding.SettingSignOut.setOnClickListener {
                startActivity(Intent(requireContext(),SignInScreen::class.java))
            }
        }
        binding.SettingChangePassword.setOnClickListener {
            showPasswordChangeDialog()
        }




        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (passwordDialog != null){
            if (passwordDialog!!.isShowing){
                passwordDialog!!.dismiss()
            }
        }
        _binding = null
    }

    fun showPasswordChangeDialog(){
        passwordDialog = Dialog(requireContext())
        passwordDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        passwordDialog?.setCancelable(false)
        val dialogBinding = FtDialogResetPasswordBinding.inflate(layoutInflater)
        val view = dialogBinding.root
        passwordDialog?.setContentView(view)
        dialogBinding.backBtn.setOnClickListener {
            if (passwordDialog!!.isShowing){
                passwordDialog!!.dismiss()
            }
        }
        dialogBinding.ChangePassword.setOnClickListener {
            val rOPassword = dialogBinding.OldPassword.text.toString()
            val rNPassword = dialogBinding.NPassword.text.toString()
            val rCPassword = dialogBinding.ConPassword.text.toString()

            if (rOPassword.isNullOrEmpty()){
                dialogBinding.OldPassword.error = "لا يمكن ترك الحقل فارغ"
                dialogBinding.OldPassword.requestFocus()
                return@setOnClickListener
            }
            if (rNPassword.isNullOrEmpty()){
                dialogBinding.NPassword.error = "لا يمكن ترك الحقل فارغ"
                dialogBinding.NPassword.requestFocus()
                return@setOnClickListener
            }
            if (rCPassword.isNullOrEmpty()){
                dialogBinding.ConPassword.error = "لا يمكن ترك الحقل فارغ"
                dialogBinding.ConPassword.requestFocus()
                return@setOnClickListener
            }
            if (rNPassword != rCPassword ){
                dialogBinding.ConPassword.error = "كلمات السر غير متطابقة"
                dialogBinding.ConPassword.requestFocus()
                return@setOnClickListener
            }
            resetPassword_Request(rOPassword,rNPassword,rCPassword)
        }
        val window: Window = passwordDialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(requireActivity().resources
                .getColor(R.color.tk_dialog_bg, null))
        )
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        passwordDialog?.show()
    }

    fun resetPassword_Request(rOPassword:String,rNPassword:String,rCPassword:String) {
        commonFuncs.showLoadingDialog(requireActivity())
        val url = Constants.APIMain + "api/auth/user/updatePassword"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.PATCH, url, Response.Listener<String> { response ->
                    Log.e("Response", response.toString())
                    commonFuncs.hideLoadingDialog()
                    if (passwordDialog!!.isShowing){
                        passwordDialog!!.dismiss()
                    }
                    commonFuncs.showDefaultDialog(requireContext(),"نجاح العملية","تم تغيير كلمة المرور بنجاح")
                }, Response.ErrorListener { error ->
                    Log.e("Error", error.toString())
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(requireContext(),"فشل إعادة التعيين",errMessage)
                        Log.e("eResponser", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(requireContext(),"فشل إعادة التعيين","حصل خطأ ما")
                        Log.e("eResponsew", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String,String>()
                    params["oldPassword"] = rOPassword
                    params["password"] = rNPassword
                    params["Cpassword"] = rCPassword
                    return params
                }
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String,String>()
                    if (commonFuncs.IsInSP(requireContext(), Constants.KeyUserToken)){
                        header["Authorization"] = "Bearer "+commonFuncs.GetFromSP(requireContext(), Constants.KeyUserToken)
                    }
                    return header
                }
            }
            val requestQueue = Volley.newRequestQueue(requireContext())
            requestQueue.add(stringRequest)
        }catch (error: JSONException){
            Log.e("Response", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }



}