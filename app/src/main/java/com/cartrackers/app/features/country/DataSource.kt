package com.cartrackers.app.features.country

import com.cartrackers.app.data.vo.Country
import kotlinx.coroutines.flow.Flow

interface DataSource {
   fun getCountry(searchItem: String): Flow<List<Country>>
}