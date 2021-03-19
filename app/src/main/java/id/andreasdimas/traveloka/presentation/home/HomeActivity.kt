package id.andreasdimas.traveloka.presentation.home

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.andreasdimas.traveloka.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
//        toolbar.subtitle = resources.getString(R.string.mySubTitle)
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar)
//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
//        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_saved, R.id.navigation_mybooking, R.id.navigation_myinbox, R.id.navigation_myaccount
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}