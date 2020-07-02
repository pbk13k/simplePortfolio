package co.kr.nawa.simpleportfolio.util.login


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import co.kr.nawa.simpleportfolio.item.SnsItem
import co.kr.nawa.simpleportfolio.util.common.logD
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*


class FaceBookLogin {

    private val TAG = "FaceBook_Login"

    private var callbackManager: CallbackManager? = null
    private val callback: (SnsItem) -> Unit
    var act:Activity ?=null
    var fragment: Fragment?=null


    constructor(act: Activity,callback: (SnsItem) -> Unit){
        this.act=act
        this.callback=callback
        init()
    }
    constructor(fragment: Fragment,callback: (SnsItem) -> Unit){
        this.fragment=fragment
        this.callback=callback
        init()
    }

    fun login() {

        if (fragment==null){
            LoginManager.getInstance().logInWithReadPermissions(act, Arrays.asList("public_profile", "email"))
        }else if (act==null){
            LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "email"))
        }

        /*
        * 로그인중
        * 프로필만 사용가능 토큰으로 결과 보내면 널나옴
        * */
//        val accessToken = AccessToken.getCurrentAccessToken()
//        logD("isExpired=${accessToken.isExpired}")
//        if(accessToken != null && !accessToken.isExpired){
//            getFacebookUserData(accessToken)
//            val profile=Profile.getCurrentProfile()
//            logD("profile=${profile.name}")
//        }

    }

    private fun init() {
        //FacebookSdk.sdkInitialize(context);

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager!!, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                getFacebookUserData(loginResult.accessToken)

            }

            override fun onCancel() {

//                val msg = Message.obtain()
//                val request = "취소되었습니다."
//                msg.obj = request
//                val data = Bundle()
//                msg.what = 0
//                msg.data = data
//                handler.sendMessage(msg)
                callback(SnsItem("취소되었습니다",SnsItem.Type.FACEBOOK))
            }

            override fun onError(e: FacebookException) {
                e.printStackTrace()
            }

        })
    }


    private fun getFacebookUserData(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { obj, response ->
            logD("TAG", "페이스북 로그인 결과$response")
//            logD("response=${response.jsonArray}")
//            logD("response=${response.jsonObject["email"]}")
            logD("obj=${obj}")

            val msg = Message.obtain()
            msg.obj = obj.toString()
            val data = Bundle()
            data.putString("url", "facebook_login")
            msg.what = 1
            msg.data = data
//            handler.sendMessage(msg)
            callback(SnsItem(response.jsonObject["email"].toString(),SnsItem.Type.FACEBOOK,true))

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
