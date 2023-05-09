package co.kr.nawa.simpleportfolio

import android.R.id
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import co.kr.nawa.simpleportfolio.di.app
import co.kr.nawa.simpleportfolio.di.appModule
import co.kr.nawa.simpleportfolio.di.viewModels
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApplication: Application() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin{
            androidContext(this@MyApplication)
            androidLogger()
//            modules(appModule)
            koin.loadModules(appModule)
//            koin.createRootScope()

        }

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val bundle = Bundle()
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id")
        bundle.putString("app", "start")
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        mFirebaseAnalytics?.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}