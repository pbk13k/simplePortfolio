package co.kr.nawa.simpleportfolio.view

import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityIntroBinding
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
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

    }
}
