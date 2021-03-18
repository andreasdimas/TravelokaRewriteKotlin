package id.andreasdimas.traveloka.presentation.hotel

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.andreasdimas.traveloka.data.models.Entries
import id.andreasdimas.traveloka.databinding.ListItemMapBinding


class SearchHotelMapAdapter constructor(
private val itemClick: (Entries?, pos: Int) -> Unit
) : ListAdapter<Entries, SearchHotelMapAdapter.ViewHolder>(DiffCallback2()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemMapBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.apply {
            bind(data, position, itemClick)
            itemView.tag = data
        }
    }


    class ViewHolder(
        val binding: ListItemMapBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Entries?,
            position: Int,
            itemClick: (Entries?, pos: Int) -> Unit
        ) {
            item?.data?.name?.let { Log.i("error", it) }

            binding.apply {
                league = item
                mainView.setOnClickListener {
                    itemClick(item, position)
                }

                tvOriginalPrice.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    text = "Striked thru text"
                }

                executePendingBindings()
            }
        }
    }
}

private class DiffCallback2 : DiffUtil.ItemCallback<Entries>() {

    override fun areItemsTheSame(oldItem: Entries, newItem: Entries): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Entries, newItem: Entries): Boolean {
        return oldItem == newItem
    }
}