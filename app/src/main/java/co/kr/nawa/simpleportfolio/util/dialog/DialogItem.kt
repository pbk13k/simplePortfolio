package co.kr.nawa.simpleportfolio.util.dialog

data class DialogItem(
        val title:String="",
        val textContent:String="",
        val sendBtnName:String="확인",
        val cancelBtnName:String="취소",
        val setCancelable:Boolean=true
)