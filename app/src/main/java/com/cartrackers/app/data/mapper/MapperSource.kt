package com.cartrackers.app.data.mapper

import com.cartrackers.app.data.vo.User
import com.cartrackers.baseplate_persistence.model.DBUser

interface MapperSource {
    fun toHashMap(from: User): Map<String, Any>
    fun mapFromRoom(from: DBUser): User
    fun mapFromDomain(from: User): DBUser
}