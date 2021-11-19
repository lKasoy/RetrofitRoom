package com.example.retrofitroom.services

data class LoadingState constructor(val status: Status, val msg: String? = null){
    companion object{
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)

    }
    enum class Status {
        SUCCESS,
        RUNNING,
        FAILED
    }
}
