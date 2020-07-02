package co.kr.nawa.simpleportfolio.util.common

import java.text.SimpleDateFormat
import java.util.*

/**
* java 1.8 부터 사용가능
* */


fun nowToAfterDay(string: String):Boolean{

    val sdf= getFormatDay()
    val now=sdf.parse(sdf.format(Date()))
    val strTime=sdf.parse(string)
    logD(
        "date=${sdf.format(now)} strT=${sdf.format(
            strTime
        )} after=${strTime.time > now.time}"
    )
    logD("now=${now.time} strTime=${strTime.time} ")
    return strTime.time >= now.time
}

fun nowToBeforeDay(string: String):Boolean{


    val sdf= getFormatDay()
    val now=sdf.parse(sdf.format(Date()))
    val strTime=sdf.parse(string)
    logD(
        "date=${sdf.format(now)} strT=${sdf.format(
            strTime
        )} after=${strTime.before(now)}"
    )
    logD("now=${now.time} strTime=${strTime.time} ")
    return strTime.time < now.time
}

fun nowToEqualsDay(string: String):Boolean{

    val sdf= getFormatDay()
    val now=sdf.parse(sdf.format(Date()))
    val strTime=sdf.parse(string)
    logD(
        "date=${sdf.format(now)} strT=${sdf.format(
            strTime
        )} after=${strTime.equals(now)}"
    )
    return strTime.time == now.time
}

fun nowToAfter(string: String):Boolean{


    val sdf= getFormat()
    val now=Date()
    val strTime=sdf.parse(string)
    logD(
        "date=${sdf.format(now)} strT=${sdf.format(
            strTime
        )} after=${strTime.after(now)}"
    )
    return strTime.after(now)
}
fun nowTobefore(string: String):Boolean{

    val sdf= getFormat()
    val now=Date()
    val strTime=sdf.parse(string)
    logD(
        "date=${sdf.format(now)} strT=${sdf.format(
            strTime
        )} after=${strTime.time > now.time}"
    )
    return strTime.time > now.time
}

fun nowToequals(string: String):Boolean{

    val sdf= getFormat()
    val now=Date()
    val strTime=sdf.parse(string)
    logD(
        "date=${sdf.format(now)} strT=${sdf.format(
            strTime
        )} after=${strTime.equals(now)}"
    )
    return strTime.equals(now)
}
fun getFormat():SimpleDateFormat{
    return SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
}

fun getFormatDay():SimpleDateFormat{
    return SimpleDateFormat("yyyy/MM/dd")
}