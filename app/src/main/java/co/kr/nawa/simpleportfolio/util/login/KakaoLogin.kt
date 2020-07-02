package co.kr.nawa.simpleportfolio.util.login


import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import co.kr.nawa.simpleportfolio.item.SnsItem
import co.kr.nawa.simpleportfolio.util.common.logD
import com.kakao.auth.*
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException


class KakaoLogin(private val act: Activity, private val callback: (SnsItem) -> Unit){

    private val context: Context
    var fragment: Fragment?=null
//    private val KAKAO_APP_KEY="37cc3c75ae9304b80ee6bd814b282d04"
//    private val disposables: CompositeDisposable = CompositeDisposable()

    constructor(act: Activity,fragment: Fragment,callback: (SnsItem) -> Unit):this(act,callback){
        this.fragment=fragment
    }

    init {
        context = act.applicationContext
        init()
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
                kakaoRequestMe()
            }

            override fun onSessionOpenFailed(exception: KakaoException) {
                //Log.e("onSessionOpenFailed",exception.toString());

            }
        })

    }


    fun login() {

//        AuthCodeClient.rx.authorize(context)
//            .observeOn(Schedulers.io())
//            .flatMap { authCode ->
//                logD("authCode=${authCode}")
//                AuthApiClient.rx.issueAccessToken(authCode)
//
//            }
//            .subscribe({
//
//            }) { error ->
//
//            }.addTo(disposables)


//        AuthCodeClient.rx.authorizeWithTalk(act.applicationContext, 1002 /* random request code for startActivity */)
//            .observeOn(Schedulers.io())
//            .flatMap { authCode -> AuthApiClient.rx.issueAccessToken(authCode) }
//            .subscribe({
//                logD("login")
//                logD(it.accessToken)
//            }) { error ->
//                logE("error111=${error.message}")
//            }.addTo(disposables)



        if (Session.getCurrentSession().checkAndImplicitOpen()) {
            logD("login_Session")
            return
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

    protected fun kakaoRequestMe() {

        UserManagement.getInstance().me(object : MeV2ResponseCallback(){
            override fun onSuccess(result: MeV2Response) {

                val profile=result.properties
                val keys=profile.keys
                logD("key.size=${keys.size}")
                for (key in keys){
                    logD("key=${key} value=${profile[key]}")
                }
                logD("email=${result.kakaoAccount.email}")

                callback(SnsItem(result.kakaoAccount.email,SnsItem.Type.KAKAO,true))
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {

            }

            override fun onFailure(errorResult: ErrorResult) {

                callback(SnsItem(errorResult.errorMessage,SnsItem.Type.KAKAO,false))
            }

        })



    }



}
