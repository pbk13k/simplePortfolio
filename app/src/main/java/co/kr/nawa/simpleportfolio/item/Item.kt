package co.kr.nawa.simpleportfolio.item

import java.io.Serializable

data class Item(
    val abv: Double = 0.0,
    val attenuation_level:  Double = 0.0,
    val brewers_tips: String = "",
    val contributed_by: String = "",
    val description: String = "",
    val ebc: Any? = Any(),
    val first_brewed: String = "",
    val food_pairing: List<String> = listOf(),
    val ibu: Any? = Any(),
    val id: Int = 0,
    val image_url: String = "",
    val name: String = "",
    val ph: Double = 0.0,
    val srm: Any? = Any(),
    val tagline: String = "",
    val target_fg: Double = 0.0,
    val target_og: Double = 0.0
):Serializable