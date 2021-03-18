package id.andreasdimas.traveloka.data.repository

import id.andreasdimas.traveloka.data.remote.ApiService

/**
 * Created by Andreas Dimas Setyoko on 2021-03-15.
 */
class HotelRepository (val apiService: ApiService) {

    fun getHotel() = apiService.getHotel()

}