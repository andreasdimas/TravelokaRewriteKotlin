package id.andreasdimas.traveloka.data.remote

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import id.andreasdimas.traveloka.BuildConfig
import id.andreasdimas.traveloka.data.pref.PreferencesHelper
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


fun provideOkHttpClient(interceptor: AuthInterceptor, context: Context): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.apply {
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        callTimeout(60, TimeUnit.SECONDS)
        addInterceptor(ChuckInterceptor(context))
        addInterceptor(interceptor)
        authenticator(MyAuthenticator(context))
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }
    }
    return httpClient.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, baseUrl: String): T {
    val moshi = Moshi.Builder().build()
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

class MyAuthenticator(private val context: Context) : Authenticator {
    private var request: Request? = null
    private var response: Response? = null
    override fun authenticate(route: Route?, response: Response): Request? {
        var tokenRefresh: String = ""


        if (!tokenRefresh.isBlank()) {
            return this.response?.request?.newBuilder()
                ?.header(
                    "Authorization",
                    "Bearer ${tokenRefresh}"
                )
                ?.build()
        } else {
            return null
        }
    }

}

class AuthInterceptor(var pref: PreferencesHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .addHeader(
                "Authorization", "Bearer " + pref.getString(PreferencesHelper.ACCESS_TOKEN_KEY)
            )
            .build()
        return chain.proceed(request)
    }
}
