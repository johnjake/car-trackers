package com.cartrackers.app.utils

import com.cartrackers.app.data.vo.CarModel

object Cars {
    val carList: List<CarModel> = listOf(
        CarModel(1, "000145", "SUV-XT6", "2021", "Cadillac"),
        CarModel(2, "000525", "SUV-XT5", "2021", "Cadillac"),
        CarModel(3, "000627", "SEDAN-CT4", "2020", "Cadillac"),
        CarModel(4, "000688", "SEDAN-CT4-V BACKWING", "2020", "Cadillac"),
        CarModel(5, "000478", "Ford Ranger 2.2L", "2020", "Ford"),
        CarModel(6, "000124", "Eclipse Cross", "2020", "Mitsubishi"),
        CarModel(7, "000342", "Mirage G4", "2020", "Mitsubishi"),
        CarModel(8, "000231", "HR-V", "2021", "Honda"),
        CarModel(9, "000110", "CR-V", "2021", "Honda"),
        CarModel(10, "000143", "CIVIC-HATCHBACK", "2021", "Honda")
    )
}
