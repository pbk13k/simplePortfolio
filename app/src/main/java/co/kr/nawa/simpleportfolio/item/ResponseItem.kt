package co.kr.nawa.simpleportfolio.item

data class ResponseItem<T>(
    val code: Int,
    var items: T
)