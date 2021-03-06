package com.example.funnytimesuserapp.AuthSection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.funnytimesuserapp.CommonSection.CommonFuncs
import com.example.funnytimesuserapp.CommonSection.Constants
import com.example.funnytimesuserapp.CommonSection.Constants.AUTH_TYPE
import com.example.funnytimesuserapp.CommonSection.Constants.EMAIL
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserID
import com.example.funnytimesuserapp.CommonSection.Constants.KeyUserToken
import com.example.funnytimesuserapp.CommonSection.Constants.USER_POSTS
import com.example.funnytimesuserapp.MainMenu
import com.example.funnytimesuserapp.databinding.FtSignInScreenBinding
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.*


class SignInScreen : AppCompatActivity() {
    lateinit var binding: FtSignInScreenBinding
    val commonFuncs = CommonFuncs()
    lateinit var callbackManager:CallbackManager
    lateinit var mGoogleSignInClient:GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FtSignInScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        callbackManager = CallbackManager.Factory.create()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("708944007620-ucphq6tnkafur4vk5p8vcbumaoa7pos6.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val signInIntent = mGoogleSignInClient.signInIntent
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("SignIn", "resultLauncher")
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
        }

        binding.UserGoogle.setOnClickListener {
            commonFuncs.showLoadingDialog(this)
            Log.e("SignIn", "Google Clicked")
            resultLauncher.launch(signInIntent)
        }


        binding.UserFaceBook.setPermissions(listOf(EMAIL, USER_POSTS))
        binding.UserFaceBook.authType = AUTH_TYPE


        binding.UserFaceBook.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    commonFuncs.showLoadingDialog(this@SignInScreen)
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            setFacebookData(result)
                        }
                    }, 1000)
                }
                override fun onCancel() {
                    Log.e("SignIn","onCancel")
                }
                override fun onError(e: FacebookException) {
                    Log.e("SignIn",e.message.toString())
                }
            })

        binding.SSignIn.setOnClickListener {
            val username = binding.SUserEmail.text.toString()
            val password = binding.SUserPassword.text.toString()
            if (username.isNullOrEmpty()){
                binding.SUserEmail.error = "???? ???????? ?????? ?????????? ????????"
                binding.SUserEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()){
                binding.SUserEmail.error = "???? ???????? ?????? ?????????? ????????"
                binding.SUserEmail.requestFocus()
                return@setOnClickListener
            }
            login_Request(username,password)
        }
        binding.SForgotPass.setOnClickListener {
            startActivity(Intent(this,PhonePasswordReset::class.java))
        }
        binding.SGoToRegister.setOnClickListener {
            startActivity(Intent(this,SignUpScreen::class.java))
            finish()
        }
        binding.ContAsGuest.setOnClickListener {
            val intent = Intent(this,MainMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Log.e("SignIn", "handleSignInResult")
        try {
            val account = completedTask.getResult(ApiException::class.java)
            social_login_Request(account.email.toString(),account.displayName.toString(),account.idToken.toString(),account.photoUrl.toString(),"google")
            // Signed in successfully, show authenticated UI.
            Log.e("SignIn", "Google "+account.displayName.toString())
            account.displayName
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("SignIn", "Google "+"signInResult:failed code=" + e.statusCode)
            commonFuncs.hideLoadingDialog()

        }
    }

    fun login_Request(username:String,password:String) {
        commonFuncs.showLoadingDialog(this)
        val url = Constants.APIMain + "api/auth/login"
        try {
        val stringRequest = object : StringRequest(
            Request.Method.POST, url, Response.Listener<String> { response ->
                Log.e("SignIn", response.toString())
                val jsonobj = JSONObject(response.toString())
                val data = jsonobj.getJSONObject("data")
                val token = data.getString("access_token")
                val userid = data.getJSONObject("user").getInt("id")
                val userphone = data.getJSONObject("user").getString("phone").toString()
                val isactive = data.getJSONObject("user").getString("status")
                if (isactive == "active"){
                    commonFuncs.WriteOnSP(this,KeyUserID,userid.toString())
                    commonFuncs.WriteOnSP(this,KeyUserToken,token)
                    commonFuncs.hideLoadingDialog()
                    val intent = Intent(this,MainMenu::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }else{
                    if (userphone == "null"){
                        val intent = Intent(this,PhoneConfirmScreen::class.java)
                        intent.putExtra("comingFrom","signin")
                        intent.putExtra("TempToken",token)
                        startActivity(intent)
                        commonFuncs.hideLoadingDialog()
                    }else{
                        val intent = Intent(this,CodeConfirmScreen::class.java)
                        intent.putExtra("comingFrom","signin")
                        intent.putExtra("TempToken",token)
                        startActivity(intent)
                        commonFuncs.hideLoadingDialog()
                        finish()
                    }

                }
            }, Response.ErrorListener { error ->
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                    val err = JSONObject(errorw)
                    val errMessage = err.getJSONObject("status").getString("message")
                    commonFuncs.showDefaultDialog(this,"?????? ?????????? ????????????",errMessage)
                    Log.e("SignIn", errorw.toString())
                } else {
                    commonFuncs.showDefaultDialog(this,"?????? ?????????? ????????????","?????? ?????? ????")
                    Log.e("SignIn", "RequestError:$error")
                }
                commonFuncs.hideLoadingDialog()

            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String,String>()
                params["email"] = username
                params["password"] = password
                return params
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        }catch (error:JSONException){
            Log.e("SignIn", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }
    fun social_login_Request(email:String,name:String,accessToken:String,avatar_img:String,provider:String) {
        val url = Constants.APIMain + "api/auth/login/social"
        try {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String> { response ->
                    Log.e("SignIn", response.toString())
                    val jsonobj = JSONObject(response.toString())
                    val data = jsonobj.getJSONObject("data")
                    val token = data.getString("access_token")
                    val userid = data.getJSONObject("user").getInt("id")
//                    val userphone = data.getJSONObject("user").getString("phone").toString()
                    val isactive = data.getJSONObject("user").getString("status")
                    if (isactive == "active"){
                        commonFuncs.WriteOnSP(this,KeyUserID,userid.toString())
                        commonFuncs.WriteOnSP(this,KeyUserToken,token)
                        commonFuncs.hideLoadingDialog()
                        val intent = Intent(this,MainMenu::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }else{
                        if (data.getJSONObject("user").isNull("phone")){
                            val intent = Intent(this,PhoneConfirmScreen::class.java)
                            intent.putExtra("comingFrom","signin")
                            intent.putExtra("TempToken",token)
                            startActivity(intent)
                            commonFuncs.hideLoadingDialog()
                        }else{
                            val intent = Intent(this,CodeConfirmScreen::class.java)
                            intent.putExtra("comingFrom","signin")
                            intent.putExtra("TempToken",token)
                            startActivity(intent)
                            commonFuncs.hideLoadingDialog()
                            finish()
                        }

                    }
                }, Response.ErrorListener { error ->
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        val errorw = String(error.networkResponse.data, Charset.forName("UTF-8"))
                        val err = JSONObject(errorw)
                        val errMessage = err.getJSONObject("status").getString("message")
                        commonFuncs.showDefaultDialog(this,"?????? ?????????? ????????????",errMessage)
                        Log.e("SignIn", errorw.toString())
                    } else {
                        commonFuncs.showDefaultDialog(this,"?????? ?????????? ????????????","?????? ?????? ????")
                        Log.e("SignIn", "RequestError:$error")
                    }
                    commonFuncs.hideLoadingDialog()

                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String,String>()
                    params["email"] = email
                    params["name"] = name
                    params["accessToken"] = accessToken
                    params["avatar_img"] = avatar_img
                    params["provider"] = provider
                    return params
                }

            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }catch (error:JSONException){
            Log.e("SignIn", error.toString())
            commonFuncs.hideLoadingDialog()
        }
    }

    private fun setFacebookData(loginResult: LoginResult) {
        val request = GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { _, response -> // Application code
            try {
                Log.i("Response", response.toString())
                val email = response!!.getJSONObject()!!.getString("email")
                val firstName = response.getJSONObject()!!.getString("first_name")
                val lastName = response.getJSONObject()!!.getString("last_name")
                val profile = Profile.getCurrentProfile()
                val id: String = profile!!.id.toString()
                val link: String = profile.getProfilePictureUri(450, 450).toString()
                Log.e("SignIn", id)
                Log.e("SignIn", link)
                social_login_Request(email, "$firstName $lastName",loginResult.accessToken.token,link,"facebook")
                Log.e("SignIn" + "Email", email)
                Log.e("SignIn" + "FirstName", firstName)
                Log.e("SignIn" + "LastName", lastName)
            } catch (e: JSONException) {
                Log.e("SignIn" , e.message.toString())
                commonFuncs.hideLoadingDialog()
                commonFuncs.showDefaultDialog(this,"?????? ?????????? ????????????","?????? ?????????? ?????????????? ???????????????? ????????????????")
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,email,first_name,last_name,gender")
        request.parameters = parameters
        request.executeAsync()
    }
}