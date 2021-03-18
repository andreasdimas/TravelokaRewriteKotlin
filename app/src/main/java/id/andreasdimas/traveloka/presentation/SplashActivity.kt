package id.andreasdimas.traveloka.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import id.andreasdimas.traveloka.R
import id.andreasdimas.traveloka.presentation.base.BaseActivity
import id.andreasdimas.traveloka.presentation.hotel.SearchHotelActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                startActivity(Intent(this, SearchHotelActivity::class.java))
            }, 1500
        )

    }

}
