package id.andreasdimas.traveloka

import android.app.Application
import id.andreasdimas.traveloka.di.myAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Andreas Dimas Setyoko on 2021-03-15.
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(myAppModule)
        }

    }
}