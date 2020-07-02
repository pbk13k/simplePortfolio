package co.kr.nawa.simpleportfolio

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.nawa.simpleportfolio.databinding.ActivityIntroBinding
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.viewModel.IntroViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
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
