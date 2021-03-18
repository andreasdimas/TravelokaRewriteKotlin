package id.andreasdimas.traveloka.data.remote

import id.andreasdimas.traveloka.data.remote.response.HotelResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Andreas Dimas Setyoko on 2021-03-15.
 */
interface ApiService {
    @GET("api/HotelSearch.php")
    fun getHotel(): Single<HotelResponse>
}