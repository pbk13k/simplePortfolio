package co.kr.nawa.simpleportfolio.view

import android.content.Intent
import android.net.Uri
import android.os.Handler
import androidx.lifecycle.Observer
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityIntroBinding
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.viewModel.IntroViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel

class Intro : ActivityBase<ActivityIntroBinding,IntroViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_intro
    override val viewModel: IntroViewModel by viewModel()

    override fun init() {

    }



    override fun init_Listener() {

    }

    override fun init_dataBinding() {
        viewModel.versionName.observe(this, Observer {
            binding.version.text = "버전 정보 : $it"
        })

        viewModel.versionCheck.observe(this, Observer {
            it?.let {
                if (it){
                    //다이얼 로그
                    startActivity( Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                }else{

                    Handler().postDelayed( {

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },2000)

                }
            }

        })
    }
}
