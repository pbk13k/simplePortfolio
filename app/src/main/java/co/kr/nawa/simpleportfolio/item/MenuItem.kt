package co.kr.nawa.simpleportfolio.item

import java.io.Serializable

data class MenuItem(
    var img: Int =0 ,
    var text:String="",
    var cls:Class<*>
): Serializable