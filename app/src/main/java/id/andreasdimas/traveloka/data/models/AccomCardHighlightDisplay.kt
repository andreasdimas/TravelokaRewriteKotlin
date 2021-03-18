package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccomCardHighlightDisplay(
    @Json(name = "icon")
    var icon: String? = "",
    @Json(name = "text")
    var text: String? = "",
    @Json(name = "backgroundColor")
    var backgroundColor: String? = "",
    @Json(name = "fontColor")
    var fontColor: String? = ""
)