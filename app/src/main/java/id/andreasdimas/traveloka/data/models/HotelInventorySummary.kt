package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotelInventorySummary(
    @Json(name = "originalRateDisplay")
    var originalRateDisplay: OriginalRateDisplay?,
    @Json(name = "hotelPromoType")
    var hotelPromoType: HotelPromoType?
)