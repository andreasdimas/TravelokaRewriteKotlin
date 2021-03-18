package id.andreasdimas.traveloka.presentation.home.ui.mybooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.andreasdimas.traveloka.R

class MyInboxFragment : Fragment() {

    private lateinit var myBookingViewModel: MyBookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myBookingViewModel =
            ViewModelProvider(this).get(MyBookingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mybooking, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        myBookingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}