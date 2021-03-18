package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Entries(
    @Json(name = "displayType")
    var displayType: String? = "",
    @Json(name = "contentType")
    var contentType: String? = "",


    @Json(name = "data")
    var data: DataEntries?
)