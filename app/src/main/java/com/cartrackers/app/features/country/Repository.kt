package com.cartrackers.app.features.country

import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.app.data.vo.Country
import com.cartrackers.baseplate_persistence.dao.CountryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    private val dao: CountryDao,
    private val mapper: Mapper): DataSource {
    override fun getCountry(searchItem: String): Flow<List<Country>> {
        return dao.getCountry(searchItem).map { db ->
            db.map {
                mapper.mapFromDBCountry(it)
            }
        }
    }
}