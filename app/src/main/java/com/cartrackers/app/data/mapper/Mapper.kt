package com.cartrackers.app.data.mapper

import com.cartrackers.app.data.vo.User
import com.cartrackers.app.utils.toJsonType
import com.cartrackers.baseplate_persistence.domain.DomainUser
import com.cartrackers.baseplate_persistence.model.DBUser

class Mapper: MapperSource {
    override fun mapFromDomain(from: User): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map[MAPPER_KEY] = DomainUser(id = from.id,
        name = from.name,
        username = from.username,
        password = from.password,
        email = from.email,
        address = from.address.toJsonType(),
        phone = from.phone,
        website = from.website,
        company = from.company.toJsonType()
        )
        return map
    }

    override fun mapFromRoom(from: DBUser): User {
        TODO("Not yet implemented")
    }

    companion object {
        const val MAPPER_KEY = "all_user"

        private var INSTANCE: Mapper? = null

        fun getInstance(): Mapper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Mapper().also { INSTANCE = it }
            }
    }
}