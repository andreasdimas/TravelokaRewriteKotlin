package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OriginalRateDisplay(
    @Json(name = "totalFare")
    var totalFare: TotalFare?
)