package id.andreasdimas.traveloka.presentation.hotel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import id.android.kmabsensi.utils.rx.SchedulerProvider
import id.android.kmabsensi.utils.rx.with
import id.andreasdimas.traveloka.data.remote.response.HotelResponse
import id.andreasdimas.traveloka.data.repository.HotelRepository
import id.andreasdimas.traveloka.presentation.base.BaseViewModel
import id.andreasdimas.traveloka.utils.UiState

/**
 * Created by Andreas Dimas Setyoko on 2021-03-15.
 */
class SearchHotelViewModel(
    val hotelRepository: HotelRepository,
    val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val leagueData = MutableLiveData<UiState<HotelResponse>>()

    fun getLeague() {
        leagueData.value = UiState.Loading()
        Log.i("error", "init")
        compositeDisposable.add(hotelRepository.getHotel()
            .with(schedulerProvider)
            .subscribe({
                leagueData.value = UiState.Success(it)
            }, {
                it.message?.let { it1 -> Log.i("error", it1) }
                leagueData.value = UiState.Error(it)
            })
        )
    }

    override fun onError(error: Throwable) {
        error.message?.let { Log.i("error", it) }
    }
}