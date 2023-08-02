package co.kr.nawa.simpleportfolio.menu.view

import androidx.lifecycle.Observer
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityMapBinding
import co.kr.nawa.simpleportfolio.menu.viewModel.MapViewModel
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapActivity : ActivityBase<ActivityMapBinding,MapViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_map
    override val viewModel: MapViewModel by viewModel()

    lateinit var googleMap:GoogleMap

    override fun init() {


        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync { maps ->
            googleMap=maps
            //viewModel.latlng.postValue(LatLng(37.569794,126.9970932))
        }
    }

    override fun init_Listener() {

    }

    override fun init_dataBinding() {
//        viewModel.latlng.observe(this, Observer {
//            if (::googleMap.isInitialized){
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 17f))
//            }
//        })
    }
}
