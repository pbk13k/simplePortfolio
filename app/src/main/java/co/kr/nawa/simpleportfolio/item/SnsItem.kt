package co.kr.nawa.simpleportfolio.item

class SnsItem(
    var email:String="",
    var type:Type=Type.NONE,
    var result:Boolean=false
){
    enum class Type {
        NONE,
        NAVER,
        KAKAO,
        GOOGLE,
        FACEBOOK
    }
}