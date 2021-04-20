package com.cartrackers.app.features.cars

import com.cartrackers.app.data.vo.CarModel
import com.cartrackers.app.utils.Cars

class Repository: DataSource {
    override suspend fun getUserCarList(): List<CarModel> {
        return Cars.carList
    }
}