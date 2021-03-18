package id.andreasdimas.traveloka.presentation.hotel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import id.andreasdimas.traveloka.R
import id.andreasdimas.traveloka.databinding.ActivityLeagueBinding
import id.andreasdimas.traveloka.utils.UiState
import kotlinx.android.synthetic.main.activity_league.*
import kotlinx.android.synthetic.main.hotel_search_linear_button_map.*
import org.koin.android.ext.android.inject
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class SearchHotelActivity : AppCompatActivity(), OnMapReadyCallback {

    private val vm: SearchHotelViewModel by inject()
    private lateinit var binding: ActivityLeagueBinding
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private lateinit var adapter: SearchHotelAdapter
    private lateinit var adapterMap: SearchHotelMapAdapter
    private lateinit var mMap: GoogleMap


    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        if (getString(R.string.maps_api_key).isEmpty()) {
            Toast.makeText(this, "Add your own API key in MapWithMarker/app/secure.properties as MAPS_API_KEY=YOUR_API_KEY", Toast.LENGTH_LONG).show()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.getView()?.visibility = View.GONE

        initAdapter()
        getLeague()
        vm.getLeague()

        val toolbar = findViewById(R.id.toolbar2) as Toolbar
        toolbar.title = resources.getString(R.string.myTitle)
        toolbar.subtitle = resources.getString(R.string.mySubTitle)
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        val tvAll = findViewById(R.id.button) as TextView
        tvAll.setOnClickListener {
            vm.getLeague()
        }

        buttonMap.setOnClickListener {
            linearBottom.visibility = View.GONE
            rv_league.visibility = View.GONE
            rv_league_map.visibility = View.VISIBLE
            horizontalView.visibility = View.GONE
            linearBottomMap.visibility = View.VISIBLE
            mapFragment?.getView()?.visibility = View.VISIBLE
            button_search_area.visibility = View.GONE
            button_return_to_top.visibility = View.GONE
        }

        buttonList.setOnClickListener {
            rv_league.visibility = View.VISIBLE
            rv_league_map.visibility = View.GONE
            horizontalView.visibility = View.VISIBLE
            linearBottom.visibility = View.VISIBLE
            linearBottomMap.visibility = View.GONE
            mapFragment?.getView()?.visibility = View.GONE
            button_search_area.visibility = View.GONE
            button_return_to_top.visibility = View.GONE
        }



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = longitude?.let { latitude?.let { it1 -> LatLng(it, it1) } }

        mMap.addMarker(sydney?.let { MarkerOptions().position(it).title("Marker in Sydney") })
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,6.0f))
    }

    fun setLocation(googleMap: GoogleMap, latitude : Double, longitude : Double, index : Int, price: String){
        mMap = googleMap
        val sydney = LatLng(latitude, longitude)
//        Toast.makeText(this, sydney.toString(), Toast.LENGTH_LONG).show()
//        mMap.addMarker(MarkerOptions().position(sydney).icon(bitmapDescriptorFromVector(this, R.drawable.ic_transparent)))
//        mMap.setInfoWindowAdapter(CustomInfoWindowForGoogleMap(this@LeagueActivity))
        mMap.addMarker(MarkerOptions().position(sydney)
            .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_star_rating,price))));

        mMap.setOnCameraMoveStartedListener { reason ->
            if (reason == OnCameraMoveStartedListener.REASON_GESTURE) {
                val isMaptouched = true
                button_search_area.visibility = View.VISIBLE
            } else if (reason == OnCameraMoveStartedListener.REASON_API_ANIMATION) {
            } else if (reason == OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION) {
            }
        }

        if(index == 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f))
        }

    }

    fun setLocationPager(googleMap: GoogleMap, latitude: String, longitude: String, price: String){
        mMap = googleMap
        val sydney = LatLng(longitude.toDouble(), latitude.toDouble())
        mMap.addMarker(MarkerOptions().position(sydney).zIndex(Float.MAX_VALUE)
            .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromViewPager(R.drawable.ic_star_rating,price))));

        mMap.setOnCameraMoveStartedListener { reason ->
            if (reason == OnCameraMoveStartedListener.REASON_GESTURE) {
                val isMaptouched = true
                button_search_area.visibility = View.VISIBLE
            } else if (reason == OnCameraMoveStartedListener.REASON_API_ANIMATION) {
            } else if (reason == OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION) {
            }
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13.0f))
    }

    class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

        var mContext = context
        var mWindow = (context as Activity).layoutInflater.inflate(R.layout.custom_info_contents, null)

        private fun rendowWindowText(marker: Marker, view: View){

            val tvTitle = view.findViewById<TextView>(R.id.title)

            tvTitle.text = marker.title

        }

        override fun getInfoContents(marker: Marker): View {
            rendowWindowText(marker, mWindow)
            return mWindow
        }

        override fun getInfoWindow(marker: Marker): View? {
//            rendowWindowText(marker, mWindow)
            return null
        }
    }

    fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_league)
        binding.apply {
            activity = this@SearchHotelActivity
            lifecycleOwner = this@SearchHotelActivity
        }
    }

    private fun initAdapter() {
        binding.apply {
            adapter = SearchHotelAdapter() { data, pos ->
                Toast.makeText(this@SearchHotelActivity, data?.data?.name, Toast.LENGTH_LONG).show()
            }
            adapterMap = SearchHotelMapAdapter() { data, pos ->
                Toast.makeText(this@SearchHotelActivity, data?.data?.name, Toast.LENGTH_LONG).show()
            }
            rvLeague.setHasFixedSize(true)
            ViewCompat.setNestedScrollingEnabled(rvLeague, false);
            rvLeague.adapter = adapter

            rvLeague.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        buttonReturnToTop.visibility = View.VISIBLE
                    } else {
                        buttonReturnToTop.visibility = View.GONE
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                        // Do something
                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        // Do something
                    } else {
//                        buttonReturnToTop.visibility = View.GONE
                    }
                }
            })



            rvLeagueMap.setHasFixedSize(true)
            ViewCompat.setNestedScrollingEnabled(rvLeagueMap, false);
            rvLeagueMap.adapter = adapterMap
            val horizontalLayoutManagaer = LinearLayoutManager(this@SearchHotelActivity, LinearLayoutManager.HORIZONTAL, false)
            rvLeagueMap.setLayoutManager(horizontalLayoutManagaer);
            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(rvLeagueMap)

            rv_league_map.visibility = View.GONE

            rvLeagueMap.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                        val visiblePosition: Int = horizontalLayoutManagaer.findFirstCompletelyVisibleItemPosition()
                        if (visiblePosition > -1) {
                            val v: View? = horizontalLayoutManagaer.findViewByPosition(visiblePosition)
                            //do something
//                            Toast.makeText(this@LeagueActivity, v?.findViewById<AppCompatTextView>(R.id.tv_idLeague)?.text.toString(), Toast.LENGTH_SHORT).show()
                            setLocationPager(mMap,v?.findViewById<AppCompatTextView>(R.id.tvLongitude)?.text.toString(),v?.findViewById<AppCompatTextView>(R.id.tvLatitude)?.text.toString(),v?.findViewById<AppCompatTextView>(R.id.tvPrice)?.text.toString())
                        }
                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        // Do something
                    } else {
//                        buttonReturnToTop.visibility = View.GONE
                    }
                }
            })


//            val list: MutableList<League> = mutableListOf()
//            for (i in 0 until 10) {
//                list.add(League())
//            }
//            adapter.submitList(list)
        }

    }


    private fun getLeague() {
        vm.leagueData.observe(this, Observer {
            when (it) {
                is UiState.Loading -> {
//                    progressBar.visibility = View.VISIBLE
                   isLoading.value = true
                    Log.i("error", isLoading.value.toString())

                }
                is UiState.Success -> {
//                    progressBar.visibility = View.GONE
//                    rv_league.visibility = View.VISIBLE
                    Log.i("error", "Success")
                    isLoading.value = false
                    it.data.leagues.apply {
                        adapter.submitList(it.data.leagues?.entries)
                        adapterMap.submitList(it.data.leagues?.entries)
                    }

                    for (i in 0 until 50) {
                        setLocation(
                            mMap,
                            it.data?.leagues?.entries?.get(i)?.data?.latitude ?: 0.0,
                            it.data?.leagues?.entries?.get(i)?.data?.longitude ?: 0.0,
                            i,
                            createCurrency(it.data?.leagues?.entries?.get(i)?.data?.lowRate ?: "")
                        )

                    }
                    Log.i("error", isLoading.value.toString())


                }
                is UiState.Error -> {
//                    progressBar.visibility = View.GONE
                    isLoading.value = false
                    Log.i("error", isLoading.value.toString())
                }
            }
        })
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun getMarkerBitmapFromView(@DrawableRes resId: Int, text : String): Bitmap? {
        val customMarkerView =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.view_custom_marker,
                null
            )
        val markerImageView =
            customMarkerView.findViewById<View>(R.id.text_message) as TextView
        markerImageView.setText(text)

        customMarkerView.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        customMarkerView.layout(
            0,
            0,
            customMarkerView.measuredWidth,
            customMarkerView.measuredHeight
        )
        customMarkerView.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(
            customMarkerView.measuredWidth, customMarkerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable: Drawable? = customMarkerView.background
        if (drawable != null) drawable.draw(canvas)
        customMarkerView.draw(canvas)
        return returnedBitmap
    }

    private fun getMarkerBitmapFromViewPager(@DrawableRes resId: Int, text : String): Bitmap? {
        val customMarkerView =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.view_custom_marker_map,
                null
            )
        val markerImageView =
            customMarkerView.findViewById<View>(R.id.text_message) as TextView
        markerImageView.setText(text)

        customMarkerView.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        customMarkerView.layout(
            0,
            0,
            customMarkerView.measuredWidth,
            customMarkerView.measuredHeight
        )
        customMarkerView.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(
            customMarkerView.measuredWidth, customMarkerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable: Drawable? = customMarkerView.background
        if (drawable != null) drawable.draw(canvas)
        customMarkerView.draw(canvas)
        return returnedBitmap
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_one) {
//            Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
            SimpleDialog.newInstance("Modify", "Message Logout").show(supportFragmentManager, SimpleDialog.TAG)
            return true
        }

        return super.onOptionsItemSelected(item)

    }


}

@BindingAdapter("imageResource")
fun ImageView.setImageResource(img :String){

    Glide.with(this.context)
        .load(img)
        .thumbnail()
        .into(this)
}

@BindingAdapter("currencyConverter")
fun AppCompatTextView.setPriceFormat(price :String){
    this.setText("Rp " + convertAmountToPriceString(price.toDouble()))
}

@BindingAdapter("reviewCount")
fun AppCompatTextView.setText(numReviews : Int){
    this.setText("(" + numReviews + ")")
}

@BindingAdapter("doubleLatitude")
fun AppCompatTextView.setTextLat(latitude : Double){
    this.setText(latitude.toString())
}
@BindingAdapter("doubleLongitude")
fun AppCompatTextView.setTextLong(longitude : Double){
    this.setText(longitude.toString())
}

@BindingAdapter("backgroundColor")
fun ConstraintLayout.setBackground(backgroundColor: String) {

    val color: Int = try {
        Color.parseColor(backgroundColor)
    } catch (e: Exception) {
        Color.parseColor("#$background")
    }
    setBackgroundColor(color)
}

@BindingAdapter("textColor")
fun AppCompatTextView.setTextColor(fontColor : String){
    val color: Int = try {
        Color.parseColor(fontColor)
    } catch (e: Exception) {
        Color.parseColor("#$background")
    }
    setTextColor(color)
}

fun createCurrency(price :String) : String{
    var currency = ""
    if(price == "")
    {
        currency =  "Rp " + convertAmountToPriceString(0.0)
    }
    else {
        currency = "Rp " + convertAmountToPriceString(price.toDouble())
    }
    return currency
}

fun convertAmountToPriceString(amount: Double): String {
    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(amount)
}