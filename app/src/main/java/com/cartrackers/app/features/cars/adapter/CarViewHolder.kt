package com.cartrackers.app.features.cars.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.R
import com.cartrackers.app.data.vo.CarModel
import com.cartrackers.app.extension.toAvatar
import com.cartrackers.app.extension.toCarModel
import de.hdodenhof.circleimageview.CircleImageView

class CarViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    val track: TextView = view.findViewById(R.id.carTrack)
    private val model: TextView = view.findViewById(R.id.carModel)
    private val year: TextView = view.findViewById(R.id.carYearSeries)
    private val brand: TextView = view.findViewById(R.id.carBrand)
    private val avatar: CircleImageView = view.findViewById(R.id.avatar)
    val content: TextView = view.findViewById(R.id.content)
    private val imgCar: ImageView = view.findViewById(R.id.imageMap)

    @SuppressLint("SetTextI18n")
    fun bind(car: CarModel) {
        val brands = car.brand
        val modelNo = car.modelNo
        val years = car.YearSeries
        val trackNo = car.trackNo
        track.text = "TrackNo: $trackNo"
        model.text = "Model: $modelNo"
        year.text = "Year: $years"
        brand.text = "Brand: $brands"
        avatar.toAvatar(car.userId, view.context)
        imgCar.toCarModel(car.userId, view.context)
    }

    companion object {
        fun create(parent: ViewGroup): CarViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed_cars, parent, false)
            return CarViewHolder(view)
        }
    }
}