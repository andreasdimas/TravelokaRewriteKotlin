package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TotalFare(
    @Json(name = "currency")
    var currency: String? = "",
    @Json(name = "amount")
    var amount: String? = ""
)