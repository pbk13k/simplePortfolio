package co.kr.nawa.simpleportfolio.util.login


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message

import android.util.Log
import androidx.fragment.app.Fragment
import co.kr.nawa.simpleportfolio.item.SnsItem
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.json.JSONObject

import java.util.Arrays

class FaceBook_Login {

    private val TAG = "FaceBook_Login"

    private var callbackManager: CallbackManager? = null
    val handler:Handler
    var act:Activity ?=null
    var fragment: Fragment?=null


    constructor(act: Activity,private val callback: (SnsItem) -> Unit){
        this.act=act
        this.handler=handler
        init()
    }
    constructor(fragment: Fragment,handler: Handler){
        this.fragment=fragment
        this.handler=handler
        init()
    }

    fun login() {
        if (fragment==null){
            LoginManager.getInstance().logInWithReadPermissions(act, Arrays.asList("public_profile", "email"))
        }else if (act==null){
            LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "email"))
        }





    }

    private fun init() {
        //FacebookSdk.sdkInitialize(context);

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager!!, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                get_facebook_data(loginResult.accessToken)

            }

            override fun onCancel() {

                val msg = Message.obtain()
                val request = "취소되었습니다."
                msg.obj = request
                val data = Bundle()
                msg.what = 0
                msg.data = data
                handler.sendMessage(msg)
            }

            override fun onError(e: FacebookException) {
                e.printStackTrace()
            }

        })
    }


    private fun get_facebook_data(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { obj, response ->
            Log.d("TAG", "페이스북 로그인 결과$response")

            val msg = Message.obtain()
            msg.obj = obj.toString()
            val data = Bundle()
            data.putString("url", "facebook_login")
            msg.what = 1
            msg.data = data
            handler.sendMessage(msg)

        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,gender")
        request.parameters = parameters
        request.executeAsync()
    }

    fun callback(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }
}
