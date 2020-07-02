package co.kr.nawa.simpleportfolio.menu.view

import android.animation.LayoutTransition
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityLoginBinding
import co.kr.nawa.simpleportfolio.item.SnsItem
import co.kr.nawa.simpleportfolio.menu.viewModel.LoginViewModel
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.common.logE
import co.kr.nawa.simpleportfolio.util.ani.move
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.util.login.FaceBookLogin
import co.kr.nawa.simpleportfolio.util.login.GoogleLogin
import co.kr.nawa.simpleportfolio.util.login.KakaoLogin
import co.kr.nawa.simpleportfolio.util.login.NaverLogin
import com.google.android.gms.auth.api.Auth
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.auth.Session
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login1.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : ActivityBase<ActivityLoginBinding, LoginViewModel>() {


    override val layoutResourceId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()


    private var mTransitionManagerForScene3: TransitionManager? = null

    var scene1: Scene? = null
    var scene2: Scene? = null
    var scene3: Scene? = null


    var kakaoLogin:KakaoLogin? =null
    var faceBookLogin:FaceBookLogin? =null
    var googleLogin:GoogleLogin? =null

    override fun init() {

        logD("init")
        val params = Bundle()
        var mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        params.putString("login", "LoginActivity")
        mFirebaseAnalytics.logEvent("loginview", params)

//        ValueAnimator().apply {
//            duration=5000
//            setEvaluator(ArgbEvaluator())
////            setIntValues(0xff, 0x00)
////            setIntValues(Color.WHITE, Color.parseColor("#E56933"))
////            setFloatValues(0f,100000f)
//            setFloatValues(1008f,0f)
//            addUpdateListener {
//                    logD("val=${it.animatedValue}")
//
//                val colors = intArrayOf(
//                    Color.parseColor("#FFFFFF"),
//                    Color.parseColor("#000000"),
////                    Color.parseColor("#FF9800"),
//                    Color.parseColor("#E56933")
//                )
//                val gd = GradientDrawable().apply {
//
//                    gradientType = GradientDrawable.LINEAR_GRADIENT
//                    setColors(colors)
//
////                    setGradientCenter(it.animatedValue as Float,0f)
////                    setGradientCenter(0f,it.animatedValue as Float)
//                    setGradientCenter(it.animatedValue as Float,it.animatedValue as Float)
//                }
//
//                bg_layout.background = gd
////                bg_layout.setBackgroundColor(it.animatedValue as Int)
//            }
//            start()
//        }


        scene1 = Scene.getSceneForLayout(bg_layout, R.layout.login1, applicationContext)
        scene2 = Scene.getSceneForLayout(bg_layout, R.layout.login2, applicationContext)
        scene3=Scene.getSceneForLayout(bg_layout,R.layout.login3,applicationContext)

        // BEGIN_INCLUDE(transition_simple)
        // You can start an automatic transition with TransitionManager.go().
//        viewModel.type.postValue(LoginViewModel.Type.MAIN)

        // BEGIN_INCLUDE(custom_transition_manager)
        // We create a custom TransitionManager for Scene 3, in which ChangeBounds and Fade
        // take place at the same time.
        mTransitionManagerForScene3=TransitionInflater.from(this)
            .inflateTransitionManager(R.transition.login2_transition_manager, bg_layout)

        viewModel.type.postValue(LoginViewModel.Type.MAIN)
    }


    override fun init_Listener() {


    }

    override fun init_dataBinding() {

        viewModel.type.observe(this, Observer {
            logD("init_dataBinding")
            when (it) {

                LoginViewModel.Type.MAIN -> {
                    mTransitionManagerForScene3?.transitionTo(scene1)
                    startAni()
                    mainListener()
                    logD("MAIN")
                }

                LoginViewModel.Type.LOGIN -> {
                    mTransitionManagerForScene3?.transitionTo(scene2)
                    loginListener()


                }
                LoginViewModel.Type.JOIN -> {
                    mTransitionManagerForScene3?.transitionTo(scene3)
                    joinListener()
                }
            }
        })
    }


    private fun mainListener() {
        logD("loginListner")
        (findViewById<ViewGroup>(R.id.login_bg1)).layoutTransition.enableTransitionType(
            LayoutTransition.CHANGING
        )

        bg_layout.findViewById<TextView>(R.id.sign_in).setOnClickListener {
            viewModel.type.postValue(LoginViewModel.Type.LOGIN)
        }


        bg_layout.findViewById<TextView>(R.id.sign_up).setOnClickListener {
            viewModel.type.postValue(LoginViewModel.Type.JOIN)
        }

        bg_layout.findViewById<TextView>(R.id.hello).setOnClickListener {
            bg_layout.findViewById<TextView>(R.id.hello).text = "Hello Hello"
        }
    }


    private fun loginListener() {
        startBg(R.drawable.bg_an_start,bg_layout.findViewById<FrameLayout>(R.id.login_bg1))


        val idText=bg_layout.findViewById<TextInputLayout>(R.id.id_text)
        val pwText=bg_layout.findViewById<TextInputLayout>(R.id.pw_text)

        idText.editText?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if (s.length>10){
                    idText.error="10자리까지 입력가능합니다."
                }else{
                    idText.error=null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        pwText.editText?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if (s.length>12){
                    pwText.error="12자리까지 입력가능합니다."
                }else{
                    pwText.error=null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        bg_layout.findViewById<TextView>(R.id.sign_in)
        .setOnClickListener {
            logD("loginListener")
            viewModel.type.postValue(LoginViewModel.Type.MAIN)
        }

        bg_layout.findViewById<ImageView>(R.id.back_btn2).setOnClickListener {
            viewModel.type.postValue(LoginViewModel.Type.MAIN)
        }

        val callback:(item:SnsItem)->Unit={
            if(it.result){
                Snackbar.make(bg_layout,it.email, Snackbar.LENGTH_LONG).show()
//                snackbarShow(it.email)
            }else{
                Snackbar.make(bg_layout,"로그인 실패/취소 되었습니다.", Snackbar.LENGTH_LONG).show()
//                snackbarShow("로그인 실패/취소 되었습니다.")
            }
            logD(it.email)
        }

        bg_layout.findViewById<ImageView>(R.id.facebook_btn).setOnClickListener {
            if(faceBookLogin==null){
                faceBookLogin=FaceBookLogin(this,callback)
            }
            faceBookLogin?.login()
        }

        bg_layout.findViewById<ImageView>(R.id.kakao_btn).setOnClickListener {
            if (kakaoLogin==null){
                kakaoLogin=KakaoLogin(act = this, callback = callback)
            }
            kakaoLogin?.login()
        }

        bg_layout.findViewById<ImageView>(R.id.naver_btn).setOnClickListener {
            NaverLogin(this,callback).login()
        }

        bg_layout.findViewById<ImageView>(R.id.google_btn).setOnClickListener {
            if (googleLogin==null){
                googleLogin= GoogleLogin(this,callback)
            }
            googleLogin?.login()
        }
    }


    private fun joinListener(){

        val idText=bg_layout.findViewById<TextInputLayout>(R.id.id_text)
        val pwText=bg_layout.findViewById<TextInputLayout>(R.id.pw_text)

        idText.editText?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if (s.length>10){
                    idText.error="10자리까지 입력가능합니다."
                }else{
                    idText.error=null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        pwText.editText?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if (s.length>12){
                    pwText.error="12자리까지 입력가능합니다."
                }else{
                    pwText.error=null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        bg_layout.findViewById<ImageView>(R.id.back_btn3).setOnClickListener {
            viewModel.type.postValue(LoginViewModel.Type.MAIN)
        }
    }


    private fun startAni() {
        move(text_layout, 1000)

//        startBg(R.drawable.bg_an_start,login_bg1)
        startBg(R.drawable.bg_an_start,bg_layout.findViewById<FrameLayout>(R.id.login_bg1))
        login_btn_layout.visibility = View.GONE
        Handler().postDelayed(Runnable {
            login_btn_layout.visibility = View.VISIBLE
            move(login_btn_layout, 500)

        }, 1000)

    }


    private fun startBg(bg: Int,view:View) {
        //bg_an1.xml 사용법
        //(bg_layout.background as TransitionDrawable).startTransition(1000)

        //bg_an_startstart.xml 사용법 (animation-list)
        view.setBackgroundResource(bg)
        (view.background as AnimationDrawable).apply {
//            setEnterFadeDuration(1000)
            setExitFadeDuration(1000)
            start()
        }
    }


    override fun onBackPressed() {
        //super.onBackPressed()
        when(viewModel.type.value){
            LoginViewModel.Type.MAIN ->{finish()}
            LoginViewModel.Type.LOGIN,LoginViewModel.Type.JOIN ->{viewModel.type.postValue(LoginViewModel.Type.MAIN)}

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logD("requestCode="+requestCode.toString()+" resultCode="+resultCode.toString())


        if(resultCode== Activity.RESULT_OK){
            //카카오 콜백
            if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
                return
            }

            //페북 콜백
            if (requestCode == 64206){
//                Common.instance.log("facebook","callback")
                faceBookLogin?.callback(
                    requestCode,
                    resultCode,
                    data!!
                )

            }else if (requestCode == GoogleLogin.google_Login_num) {

//            Common.instance.log("requestCode7777="+requestCode.toString(),"resultCode="+resultCode.toString())

//            var key_data: MutableIterator<String> =data.extras.keySet().iterator()
                //Iterator<String> data=  remoteMessage.getData().keySet().iterator();
//            while (key_data.hasNext()){
//                var key=key_data.next()
//                Common.instance.log("key",key)
//                Log.i("key="+key,data.data.get(key));
//            }
//            Common.instance.log("dddd",data.extras.get("googleSignInStatus").toString())


                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

                if (result != null) {
                    if (result.isSuccess) {

                        val token = result.signInAccount!!.idToken
                        val credential = GoogleAuthProvider.getCredential(token, null)
                        googleLogin?.signInWithCredential(credential)
                    } else {
                        logE( "Google Login Failed." + result.status)
                    }
                }
            }

        }
    }


}
