package co.kr.nawa.simpleportfolio.util.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.lifecycle.Transformations.switchMap
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.item.SnsItem
import co.kr.nawa.simpleportfolio.util.`fun`.logD
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Naver_Login(private val act: Activity, private val callback: (SnsItem) -> Unit) {

    private val TAG = "Naver_Login"

    private val mOAuthLoginInstance: OAuthLogin
    private var mOAuthLoginHandler: OAuthLoginHandler? = null
    private val context: Context
    private var accessToken: String? = null
    private var tokenType: String? = null

    val OAUTH_CLIENT_ID = "Pk5cz4EJ8C300rxsotuQ"
    val OAUTH_CLIENT_SECRET = "VBmsfvDTuA"

    init {
        this.context = act.application
        mOAuthLoginInstance = OAuthLogin.getInstance()
        val OAUTH_CLIENT_NAME = context.getString(R.string.app_name)
        mOAuthLoginInstance.init(context, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME)
        init_Handler()

    }

    fun login() {
        mOAuthLoginInstance.startOauthLoginActivity(act, mOAuthLoginHandler)
    }


    @SuppressLint("HandlerLeak")
    private fun init_Handler() {
        mOAuthLoginHandler = object : OAuthLoginHandler() {
            @SuppressLint("CheckResult")
            override fun run(success: Boolean) {
                if (success) {

                    accessToken = mOAuthLoginInstance.getAccessToken(context)
                    val refreshToken = mOAuthLoginInstance.getRefreshToken(context)
                    val expiresAt = mOAuthLoginInstance.getExpiresAt(context)
                    tokenType = mOAuthLoginInstance.getTokenType(context)
//                    logD("myLog", "accessToken  " + accessToken)
//                    Log.d("myLog", "refreshToken  " + refreshToken)
//                    Log.d("myLog","String.valueOf(expiresAt)  " + String.valueOf(expiresAt))
//                    Log.d("myLog", "tokenType  " + tokenType)
//                    Log.d("myLog","mOAuthLoginInstance.getState(mContext).toString()  " + mOAuthLoginInstance.getState(context).toString())
//                    RequestApiTask().execute()

                    //로그인이 성공하면  네이버에 계정값들을 가져온다.
                    Observable.fromCallable{
                        val url = "https://openapi.naver.com/v1/nid/me"
                        val at = mOAuthLoginInstance.getAccessToken(context)
                       return@fromCallable mOAuthLoginInstance.requestApi(context, at, url)
                    }
                        .map{
                            var email=JsonParser().parse(it).asJsonObject.get("response").asJsonObject.get("email").asString
                            return@map SnsItem(email,SnsItem.Type.NAVER)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            callback(it)
                        },{

                        })


                } else {

                    val errorCode = mOAuthLoginInstance.getLastErrorCode(context).code
                    val errorDesc = mOAuthLoginInstance.getLastErrorDesc(context)
                    Toast.makeText(context, "로그인이 취소/실패 하였습니다.!", Toast.LENGTH_SHORT).show()
                    //                    Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                    //                    Log.e("error","errorCode : " + errorCode + ", errorDesc : " + errorDesc);
                }
            }
        }

    }


    private inner class RequestApiTask : AsyncTask<Void, Void, Void>() {

        override fun onPreExecute() {}

        override fun doInBackground(vararg params: Void): Void? {

            //            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml"; xml
            val url = "https://openapi.naver.com/v1/nid/me"
            val at = mOAuthLoginInstance.getAccessToken(context)
            val user_json = mOAuthLoginInstance.requestApi(context, at, url)

            val msg = Message.obtain()
            msg.obj = user_json
            val data = Bundle()
            data.putString("url", "naver_login")
            msg.what = 1
            msg.data = data

            var email=JsonParser().parse(user_json).asJsonObject.get("response").asJsonObject.get("email").asString
//            callback(SnsItem(email,"naver"))

//            handler.sendMessage(msg)

            return null
        }

    }

}





