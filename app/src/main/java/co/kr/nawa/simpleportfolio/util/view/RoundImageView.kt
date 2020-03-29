package co.kr.nawa.simpleportfolio.util.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.logD


class RoundImageView(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatImageView(context,attrs) {

    var radius = 18.0f


    var postion:Int

    val none=0
    val topLeft=1
    val topRight=2
    val bottomRight=4
    val bottomLeft=8


    init {
        val a2 = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        if(a2.getFloat(R.styleable.RoundImageView_radius,0f)>0){
            radius=a2.getFloat(R.styleable.RoundImageView_radius,0f)
        }

        postion=a2.getInt(R.styleable.RoundImageView_postion,none)


//        logD("radius=${radius}")
//        logD("topLeft=${containsFlag(postion,topLeft)}")
//        logD("topRight=${containsFlag(postion,topRight)}")
//        logD("bottomRight=${containsFlag(postion,bottomRight)}")
//        logD("bottomLeft=${containsFlag(postion,bottomLeft)}")
    }

    fun containsFlag(po:Int,flag:Int):Boolean{
        return po or flag == po
    }


    override fun onDraw(canvas: Canvas) {
        val clipPath = Path()
        val rect = RectF(0F, 0F, this.width.toFloat(), this.height.toFloat())
        var ra:FloatArray
        if (postion==0 ) {

            ra=floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        }else{
            ra=FloatArray(8) {0f}
            ra=setRadius(containsFlag(postion,topLeft) ,0,ra)
            ra=setRadius(containsFlag(postion,topRight) ,2,ra)
            ra=setRadius(containsFlag(postion,bottomRight) ,4,ra)
            ra=setRadius(containsFlag(postion,bottomLeft) ,6,ra)
        }

        clipPath.addRoundRect(rect, ra, Path.Direction.CW)
        canvas.clipPath(clipPath)
        super.onDraw(canvas)
    }

    fun setRadius(check:Boolean,po:Int,arr:FloatArray):FloatArray{
        if (check){
            arr[po]=radius
            arr[po+1]=radius
        }
        return arr
    }
}