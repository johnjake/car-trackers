package com.cartrackers.app.features.cars.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.CarModel

class CarAdapter: RecyclerView.Adapter<CarViewHolder>() {

    private var dataSources: List<CarModel> = emptyList()
    var dataSource: List<CarModel> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSource.size
}