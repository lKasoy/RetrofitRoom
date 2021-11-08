package com.example.retrofitroom.services

import com.example.retrofitroom.data.model.entity.UsersTable

data class Event<out T>(val status: Status, val data: List<UsersTable>?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null)
        }

        fun <T> success(data: List<UsersTable>?): Event<T> {
            return Event(Status.SUCCESS, data)
        }

        fun <T> loadFromDb(data: List<UsersTable>?): Event<T>{
            return Event(Status.DAO, data)
        }
    }
}