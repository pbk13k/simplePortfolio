package co.kr.nawa.simpleportfolio.menu.view

import android.animation.ArgbEvaluator
import android.animation.LayoutTransition
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityLoginBinding
import co.kr.nawa.simpleportfolio.item.SnsItem
import co.kr.nawa.simpleportfolio.menu.viewModel.LoginViewModel
import co.kr.nawa.simpleportfolio.util.ani.move
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.common.logE

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.kakao.sdk.common.KakaoSdk

import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : ActivityBase<ActivityLoginBinding, LoginViewModel>() {


    override val layoutResourceId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()


    private var mTransitionManagerForScene3: TransitionManager? = null

    var scene1: Scene? = null
    var scene2: Scene? = null
    var scene3: Scene? = null


    private val kakaoAppKey = "37cc3c75ae9304b80ee6bd814b282d04"

    val OAUTH_CLIENT_ID = "Pk5cz4EJ8C300rxsotuQ"
    val OAUTH_CLIENT_SECRET = "VBmsfvDTuA"
    lateinit var OAUTH_CLIENT_NAME: String

    override fun init() {
        OAUTH_CLIENT_NAME = getString(R.string.app_name)

        KakaoSdk.init(this, kakaoAppKey)
        //logD("init")
//        val params = Bundle()
//        var mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
//        params.putString("login", "LoginActivity")
//        mFirebaseAnalytics.logEvent("loginview", params)

        ValueAnimator().apply {
            duration = 5000
            setEvaluator(ArgbEvaluator())
//            setIntValues(0xff, 0x00)
//            setIntValues(Color.WHITE, Color.parseColor("#E56933"))
//            setFloatValues(0f,100000f)
            setFloatValues(1008f, 0f)
            addUpdateListener {


                val colors = intArrayOf(
                    Color.parseColor("#FFFFFF"),
                    Color.parseColor("#000000"),
//                    Color.parseColor("#FF9800"),
                    Color.parseColor("#E56933")
                )
                val gd = GradientDrawable().apply {

                    gradientType = GradientDrawable.LINEAR_GRADIENT
                    setColors(colors)

//                    setGradientCenter(it.animatedValue as Float,0f)
//                    setGradientCenter(0f,it.animatedValue as Float)
                    setGradientCenter(it.animatedValue as Float, it.animatedValue as Float)
                }

                binding.bgLayout.background = gd
//                binding.bgLayout.setBackgroundColor(it.animatedValue as Int)
            }
            start()
        }


        scene1 = Scene.getSceneForLayout(binding.bgLayout, R.layout.login1, applicationContext)
        scene2 = Scene.getSceneForLayout(binding.bgLayout, R.layout.login2, applicationContext)
        scene3 = Scene.getSceneForLayout(binding.bgLayout, R.layout.login3, applicationContext)

        // BEGIN_INCLUDE(transition_simple)
        // You can start an automatic transition with TransitionManager.go().
//        viewModel.type.postValue(LoginViewModel.Type.MAIN)

        // BEGIN_INCLUDE(custom_transition_manager)
        // We create a custom TransitionManager for Scene 3, in which ChangeBounds and Fade
        // take place at the same time.
        mTransitionManagerForScene3 = TransitionInflater.from(this)
            .inflateTransitionManager(R.transition.login2_transition_manager, binding.bgLayout)


        viewModel.setType(LoginViewModel.Type.MAIN)
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

        (findViewById<ViewGroup>(R.id.login_bg1)).layoutTransition.enableTransitionType(
            LayoutTransition.CHANGING
        )

        binding.bgLayout.findViewById<TextView>(R.id.sign_in).setOnClickListener {
            viewModel.setType(LoginViewModel.Type.LOGIN)
        }


        binding.bgLayout.findViewById<TextView>(R.id.sign_up).setOnClickListener {
            viewModel.setType(LoginViewModel.Type.JOIN)
        }

        binding.bgLayout.findViewById<TextView>(R.id.hello).setOnClickListener {
            binding.bgLayout.findViewById<TextView>(R.id.hello).text = "Hello Hello"
        }
    }


    private fun loginListener() {
        startBg(R.drawable.bg_an_start, binding.bgLayout.findViewById<FrameLayout>(R.id.login_bg1))


        val idText = binding.bgLayout.findViewById<TextInputLayout>(R.id.id_text)
        val pwText = binding.bgLayout.findViewById<TextInputLayout>(R.id.pw_text)

        idText.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length > 10) {
                    idText.error = "10자리까지 입력가능합니다."
                } else {
                    idText.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        pwText.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length > 12) {
                    pwText.error = "12자리까지 입력가능합니다."
                } else {
                    pwText.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.bgLayout.findViewById<TextView>(R.id.sign_in)
            .setOnClickListener {

                viewModel.setType(LoginViewModel.Type.MAIN)
            }

        binding.bgLayout.findViewById<ImageView>(R.id.back_btn2).setOnClickListener {
            viewModel.setType(LoginViewModel.Type.MAIN)
        }

        binding.bgLayout.findViewById<LinearLayout>(R.id.kakao_btn).setOnClickListener {
            logD("kakaoBtn")
            viewModel.kakaoLogin(this)
        }

        binding.bgLayout.findViewById<LinearLayout>(R.id.google_btn).setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(context.getString(R.string.google_maps_key))
                .requestEmail()
                .build()

            googleLauncher.launch(GoogleSignIn.getClient(baseContext, gso).signInIntent)
        }
    }


    val callback: (item: SnsItem) -> Unit = {
        if (it.result) {
            Snackbar.make(binding.bgLayout, it.email, Snackbar.LENGTH_LONG).show()
//                snackbarShow(it.email)
        } else {
            Snackbar.make(binding.bgLayout, "로그인 실패/취소 되었습니다.", Snackbar.LENGTH_LONG).show()
//                snackbarShow("로그인 실패/취소 되었습니다.")
        }
        logD(it.email)
    }


    private fun joinListener() {

        val idText = binding.bgLayout.findViewById<TextInputLayout>(R.id.id_text)
        val pwText = binding.bgLayout.findViewById<TextInputLayout>(R.id.pw_text)

        idText.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length > 10) {
                    idText.error = "10자리까지 입력가능합니다."
                } else {
                    idText.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        pwText.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length > 12) {
                    pwText.error = "12자리까지 입력가능합니다."
                } else {
                    pwText.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        binding.bgLayout.findViewById<ImageView>(R.id.back_btn3).setOnClickListener {
            viewModel.setType(LoginViewModel.Type.MAIN)
        }
    }


    private fun startAni() {
        move(binding.bgLayout.findViewById<LinearLayout>(R.id.text_layout), 1000)

//        startBg(R.drawable.bg_an_start,login_bg1)
        startBg(R.drawable.bg_an_start, binding.bgLayout.findViewById<FrameLayout>(R.id.login_bg1))
        binding.bgLayout.findViewById<LinearLayout>(R.id.login_btn_layout).visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.bgLayout.findViewById<LinearLayout>(R.id.login_btn_layout).visibility =
                View.VISIBLE
            move(binding.bgLayout.findViewById<LinearLayout>(R.id.login_btn_layout), 500)

        }, 1000)

    }


    private fun startBg(bg: Int, view: View) {
        //bg_an1.xml 사용법
        //(binding.bgLayout.background as TransitionDrawable).startTransition(1000)

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
        when (viewModel.type.value) {
            LoginViewModel.Type.MAIN -> {
                finish()
            }

            LoginViewModel.Type.LOGIN, LoginViewModel.Type.JOIN -> {
                viewModel.setType(LoginViewModel.Type.MAIN)
            }

            else -> {}
        }
    }

    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (result.resultCode == RESULT_OK) {

                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(result.data!!)

                if (result != null) {
                    var email = ""
                    if (result.isSuccess) {
                        email = result.signInAccount!!.email!!
                        //val token = result.signInAccount!!.idToken
                        //logD("token=${token}")
                        logD("token=${result.signInAccount!!.email}")
                        logD("token=${result.signInAccount!!.id}")

                    } else {
                        logE("Google Login Failed." + result.status)
                    }

                    callback(SnsItem(email, SnsItem.Type.GOOGLE, result.isSuccess))
                }
            }

        }

}
