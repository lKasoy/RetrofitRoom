package com.example.retrofitroom.services

data class Event<out T>(val status: Status, val data: T?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null)
        }

        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, data)
        }

        fun <T> error(data : T?): Event<T> {
            return Event(Status.ERROR, data)
        }
    }
}