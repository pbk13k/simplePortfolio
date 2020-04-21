package co.kr.nawa.simpleportfolio.util.login


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import com.kakao.auth.*
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.util.exception.KakaoException


class Kakao_Login(act: Activity,  handler: Handler){
    private val context: Context
    private val TAG = "Kakao_Login"
    val handler:Handler
    var act:Activity ?=null
    var fragment: Fragment?=null

    init {
        this.act=act
        context = act.applicationContext
        this.handler=handler
        init()
    }



    constructor(act: Activity,fragment: Fragment, handler: Handler):this(act,handler){
        this.fragment=fragment
    }

    fun init(){
        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(object : KakaoAdapter() {
                override fun getApplicationConfig(): IApplicationConfig {

                    return IApplicationConfig { context }
                }

                override fun getSessionConfig(): ISessionConfig {
                    return object : ISessionConfig {
                        override fun getAuthTypes(): Array<AuthType> {
                            return arrayOf(AuthType.KAKAO_TALK)
                        }

                        override fun isUsingWebviewTimer(): Boolean {
                            return false
                        }

                        override fun isSecureMode(): Boolean {
                            return false
                        }

                        override fun getApprovalType(): ApprovalType {
                            return ApprovalType.INDIVIDUAL
                        }

                        override fun isSaveFormData(): Boolean {
                            return true
                        }
                    }
                }
            })

        }
        Session.getCurrentSession().addCallback(object : ISessionCallback {
            override fun onSessionOpened() {
                //Log.i("onSessionOpened","onSessionOpened");
                KakaorequestMe()
            }

            override fun onSessionOpenFailed(exception: KakaoException) {
                //Log.e("onSessionOpenFailed",exception.toString());

            }
        })
    }


    fun login() {

        if (Session.getCurrentSession().checkAndImplicitOpen()) {

        }

        if (fragment!=null){
            Session.getCurrentSession().open(AuthType.KAKAO_TALK, fragment)
        }else if (act!=null){
            Session.getCurrentSession().open(AuthType.KAKAO_TALK, act)
        } else{

        }

        //        Session.getCurrentSession().checkAndImplicitOpen();
    }


    fun removeCallback() {
        Session.getCurrentSession().removeCallback(object : ISessionCallback {
            override fun onSessionOpened() {
                //Log.i("onSessionOpened","onSessionOpened");
            }

            override fun onSessionOpenFailed(exception: KakaoException) {
                //Log.e("onSessionOpenFailed",exception.toString());
            }
        })
    }

    protected fun KakaorequestMe() {

        UserManagement.getInstance().requestMe(object : MeResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {
                //                String message = "failed to get user info. msg=" + errorResult;
                //                Logger.d(message);
                val msg = Message.obtain()
                val request = "취소되었습니다."
                msg.obj = request
                val data = Bundle()
                msg.what = 0
                msg.data = data
                handler.sendMessage(msg)
                //redirectLoginActivity();
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                //redirectLoginActivity();
            }

            override fun onSuccess(userProfile: UserProfile) {
                val msg = Message.obtain()

                msg.obj = userProfile.email
                userProfile.id
                //                Log.d("id"," "+userProfile.getId());
                //                Log.d("nick"," "+userProfile.getNickname());
                //                Log.d("img"," "+userProfile.getProfileImagePath());
                var img_url = ""
                if (userProfile.profileImagePath != "null" || userProfile.profileImagePath != null) {
                    img_url = userProfile.profileImagePath
                }
                val data = Bundle()
                data.putString("url", "kakao_login")
                data.putString("nickname", userProfile.nickname)
                data.putString("profile_image", img_url)
                msg.what = 1
                msg.data = data
                handler.sendMessage(msg)

            }

            override fun onNotSignedUp() {
                Log.d(TAG, "onNotSignedUp")
                //showSignup();
            }
        })
    }
}
