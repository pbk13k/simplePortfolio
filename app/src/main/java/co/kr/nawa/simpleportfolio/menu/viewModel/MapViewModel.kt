package co.kr.nawa.simpleportfolio.menu.viewModel

import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.util.async.Repository
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic
import com.google.android.gms.maps.model.LatLng

class MapViewModel(private val repository: Repository):ViewModelBasic() {
    var latlng= MutableLiveData<LatLng>()

}