package co.kr.nawa.simpleportfolio.util.ani

import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateInterpolator
import co.kr.nawa.simpleportfolio.util.common.logD


val interpolatorV= AccelerateInterpolator()

fun move(view: View,time:Long){
    view.apply {
//        scaleX=0f
//        scaleY=0f
        alpha=0f
        translationY=120f
    }

    view.animate().apply {
//        scaleX(1f)
//        scaleY(1f)
        alpha(1f)
        translationY(-30f )
        duration=time
        interpolator = interpolatorV
        withEndAction {

            view.animate().translationY(0f).setInterpolator(interpolatorV).setDuration(100).start()
        }
    }
}

fun move(view: View,time:Long,startY:Float,endY:Float){
    view.apply {
//        scaleX=0f
//        scaleY=0f
//        alpha=0f
        translationY=startY
    }

    view.animate().apply {
//        scaleX(1f)
//        scaleY(1f)
//        alpha(1f)
        translationY(endY )
        duration=time
        interpolator = interpolatorV

    }
}


fun move(view: View, time:Long, runnable: () -> Unit){
    view.apply {
//        scaleX=0f
//        scaleY=0f
        alpha=0f
        translationY=120f
    }

    view.animate().apply {
//        scaleX(1f)
//        scaleY(1f)
        alpha(1f)
        translationY(0f )
        duration=time
        interpolator = interpolatorV
        withEndAction(runnable)
    }
}



fun moveStart(ani: ViewPropertyAnimator){
    ani.start()
}