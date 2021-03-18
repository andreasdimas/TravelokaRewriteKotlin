package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotelPromoType(
    @Json(name = "promoType")
    var promoType: String? = "",
    @Json(name = "promoDescription")
    var promoDescription: String? = ""
)