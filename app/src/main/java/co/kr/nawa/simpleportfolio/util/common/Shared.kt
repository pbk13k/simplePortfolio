package co.kr.nawa.simpleportfolio.util.common

import android.app.Activity
import android.content.Context

val PREF="Shared"

// 값 넣기(String)
fun setPref(context: Context, key: String, value: String) {
    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(key, value)
    editor.commit()
}

// 값 불러오기(string)
fun getPref(context: Context, key: String): String {
    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    return (if (pref.contains(key)) {
        pref.getString(key, "")
    } else {
        ""
    }) as String
}


// 값 넣기(boolean)
fun setPref(context: Context, key: String, value: Boolean) {
    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putBoolean(key, value)
    editor.commit()
}

// 값 불러오기
fun getPref(context: Context, key: String, value: Boolean): Boolean {

    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    return if (pref.contains(key)) {
        pref.getBoolean(key, value)
    } else {
        false
    }
}

// 값 넣기(boolean)
fun setPref(context: Context, key: String, value: Int) {
    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putInt(key, value)
    editor.commit()
}
// 값 불러오기
fun getPref(context: Context, key: String, value: Int): Int {

    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    return if (pref.contains(key)) {
        pref.getInt(key, value)
    } else {
        -1
    }
}


// 값(Key Data) 삭제하기
fun delPref(context: Context, key: String) {
    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.remove(key)
    editor.commit()
}

// 값(ALL Data) 삭제하기
fun delAllPref(context: Context) {
    val pref = context.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.clear()
    editor.commit()
}