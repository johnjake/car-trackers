package com.cartrackers.app.features.cars

import com.cartrackers.app.data.vo.CarModel

interface DataSource {
    suspend fun getUserCarList(): List<CarModel>
}