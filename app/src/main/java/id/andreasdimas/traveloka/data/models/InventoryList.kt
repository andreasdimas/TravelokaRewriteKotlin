package id.andreasdimas.traveloka.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InventoryList(
    @Json(name = "id")
    var id: String? = "",
    @Json(name = "name")
    var name: String? = "",
    @Json(name = "displayName")
    var displayName: String? = "",
    @Json(name = "address")
    var address: String? = "",
    @Json(name = "region")
    var region: String? = "",
    @Json(name = "starRating")
    var starRating: String? = "",
    @Json(name = "userRating")
    var userRating: String? = "",
    @Json(name = "numReviews")
    var numReviews: Int? = 0,
    @Json(name = "userRatingInfo")
    var userRatingInfo: String? = "",
    @Json(name = "latitude")
    var latitude: String? = "",
    @Json(name = "longitude")
    var longitude: String? = "",
    @Json(name = "lowRate")
    var lowRate: String = "",
    @Json(name = "highRate")
    var highRate: String? = "",
    @Json(name = "lastBookingDeltaTime")
    var lastBookingDeltaTime: String? = "",
    @Json(name = "numPeopleViews")
    var numPeopleViews: String? = "",
    @Json(name = "propertyListing")
    var propertyListing: String? = "",
    @Json(name = "inventoryUnitDisplay")
    var inventoryUnitDisplay: String? = "",
    @Json(name = "imageUrl")
    var imageUrl: String? = "",
    @Json(name = "hotelSeoUrl")
    var hotelSeoUrl: String? = "",
    @Json(name = "loyaltyAmount")
    var loyaltyAmount: String? = "",
    @Json(name = "accomLoyaltyEligibilityStatus")
    var accomLoyaltyEligibilityStatus: String? = "",
    @Json(name = "accomPropertyType")
    var accomPropertyType: String? = "",
    @Json(name = "indexPosition")
    var indexPosition: String? = "",

    @Json(name = "inventoryList")
    var inventoryList: List<InventoryList>?,

    @Json(name = "hotelInventorySummary")
    var hotelInventorySummary: HotelInventorySummary?,

    @Json(name = "accomCardHighlightDisplay")
    var accomCardHighlightDisplay: AccomCardHighlightDisplay?

)