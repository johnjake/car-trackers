package com.cartrackers.app.features.cars.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.CarModel
import com.cartrackers.app.databinding.ItemFeedCarsBinding
import com.cartrackers.app.extension.toAvatar
import com.cartrackers.app.extension.toCarModel
import com.cartrackers.app.features.common.ProfileOnClickListener

class CarsAdapter(private val listener: ProfileOnClickListener): ListAdapter<CarModel, CarsAdapter.CarsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val binding = ItemFeedCarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    inner class CarsViewHolder(private val binding: ItemFeedCarsBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(car: CarModel, listener: ProfileOnClickListener) {
            binding.apply {
                if(car.userId!=0) {
                    car.userId.let { imageMap.toCarModel(it, binding.root.context) }
                    car.userId.let { avatar.toAvatar(it, binding.root.context) }
                }
                carBrand.text = "Brand: ${car.brand}"
                carYearSeries.text = "Year: ${car.YearSeries}"
                carModel.text = "Model: ${car.modelNo}"
                carTrack.text =  "TrackNo: ${car.trackNo}"
                content.text = "The model of a car is the name used by a manufacturer to market a range of similar cars. The methods used by car manufacturers to categorise their product range into models varies between manufacturers"
                avatar.setOnClickListener {
                    listener.onClickListener(car.userId)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CarModel>() {
        override fun areItemsTheSame(oldItem: CarModel, newItem: CarModel) =
            oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: CarModel, newItem: CarModel) =
            oldItem == newItem
    }
}

