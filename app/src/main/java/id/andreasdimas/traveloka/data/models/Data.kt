package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "numOfHotels")
    var numOfHotels: String? = "",
    @Json(name = "countryName")
    var countryName: String? = "",
    @Json(name = "entries")
    var entries: List<Entries>?
)
