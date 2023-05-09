package co.kr.nawa.simpleportfolio.menu.viewModel

import android.content.Context
import android.hardware.usb.UsbDevice.getDeviceId
import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.item.OauthItem
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.async.Repository
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic
import co.kr.nawa.simpleportfolio.util.common.logE
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LoginViewModel(private val repository: Repository) : ViewModelBasic() {

    var uuid: String = ""
    var oauth_token: String = "37cc3c75ae9304b80ee6bd814b282d04"

    enum class Type constructor() {
        MAIN, LOGIN, JOIN
    }


    var type = MutableLiveData<Type>()


    fun kakaoLogin(context: Context) {

        // 카카오톡으로 로그인
        Single.just(UserApiClient.instance.isKakaoTalkLoginAvailable(context))
            .flatMap { available ->
                if (available) UserApiClient.rx.loginWithKakaoTalk(context) //카카오 앱 로그인
//                if (available) UserApiClient.rx.loginWithKakaoAccount(context)
                else UserApiClient.rx.loginWithKakaoAccount(context)
            }
            //제설치시 문제가 됨.
//            .zipWith(UserApiClient.rx.accessTokenInfo().subscribeOn(Schedulers.io()),{
//                    _,tokenInfo -> tokenInfo
//            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                logE("로그인 ㅁㅁㅁ")
                oauth_token = token.refreshToken

                logD("로그인 성공 ${token.refreshToken}")
                getKakaoId()
            }, { error ->
                logE("로그인 실패${error.message}")
                if (error is KakaoSdkError) {
                    logE("로그인 실패${error}")
                }

                //회원가입
            }).addTo(compositeDisposable)


    }

    private fun getKakaoId() {

        UserApiClient.rx.accessTokenInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ tokenInfo ->
                uuid = tokenInfo.id.toString()
//                login()
//                kakao()
            }, { error ->
                if (error is KakaoSdkError) {
                    logE("로그인 실패${error}")
                }

            })
            .addTo(compositeDisposable)

    }


}