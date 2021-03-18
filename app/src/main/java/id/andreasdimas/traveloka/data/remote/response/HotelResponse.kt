package id.andreasdimas.traveloka.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.andreasdimas.traveloka.data.models.Data

@JsonClass(generateAdapter = true)
data class HotelResponse(
    @Json(name = "data")
    var leagues: Data?
)