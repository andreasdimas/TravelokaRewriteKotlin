package id.andreasdimas.traveloka.di

import id.android.kmabsensi.utils.rx.AppSchedulerProvider
import id.android.kmabsensi.utils.rx.SchedulerProvider
import id.andreasdimas.traveloka.data.pref.PreferencesHelper
import id.andreasdimas.traveloka.data.remote.*
import id.andreasdimas.traveloka.data.repository.HotelRepository
import id.andreasdimas.traveloka.presentation.hotel.SearchHotelViewModel
import id.andreasdimas.traveloka.utils.Const
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Andreas Dimas Setyoko on 2021-03-15.
 */

val appModule = module {
    single { PreferencesHelper(androidContext()) }
    single { AuthInterceptor(get()) }
    single { provideOkHttpClient(get(), androidContext()) }
    single { createWebService<ApiService>(get(), Const.appUrl) }
    single { AppSchedulerProvider() as SchedulerProvider }
}

val dataModule = module {
    single { HotelRepository(get()) }
}

val viewModelModule = module {
    viewModel { SearchHotelViewModel(get(), get()) }
}

val myAppModule = listOf(
    appModule,
    dataModule,
    viewModelModule
)
