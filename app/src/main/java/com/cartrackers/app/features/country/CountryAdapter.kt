package com.cartrackers.app.features.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cartrackers.app.data.vo.Country
import com.cartrackers.app.databinding.ItemStringCountryBinding
import com.cartrackers.app.extension.toCountry

class CountryAdapter(private val listener: OnCountryClick): ListAdapter<Country, CountryAdapter.CountryViewHolder>(DiffCallback()) {

    interface OnCountryClick {
        fun onClickListener(country: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemStringCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    inner class CountryViewHolder(private val binding: ItemStringCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country, itemListener: OnCountryClick) {
            if(country.id>0) {
                binding.avatarCountry.toCountry(country.id, binding.root.context)
            }
            binding.apply {
                countryName.text = country.name
                countryName.setOnClickListener {
                    itemListener.onClickListener(country.name)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Country, newItem: Country) =
            oldItem == newItem
    }
}