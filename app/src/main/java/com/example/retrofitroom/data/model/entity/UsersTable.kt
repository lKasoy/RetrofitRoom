package com.example.retrofitroom.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_users")
data class UsersTable(
    var gender: String,
    var title: String,
    var first: String,
    var last: String,
    var large: String,
    @PrimaryKey var uuid: String,
    var phone: String
) {

    companion object {
        fun toDatabase(usersApi: Users): List<UsersTable> {
             return usersApi.results.map {
                UsersTable(
                    gender = it.gender,
                    title = it.name.title,
                    first = it.name.first,
                    last = it.name.last,
                    large = it.picture.large,
                    uuid = it.login.uuid,
                    phone = it.phone
                )
            }
        }
    }
}

