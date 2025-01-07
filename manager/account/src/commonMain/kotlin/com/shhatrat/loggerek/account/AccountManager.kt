package com.shhatrat.loggerek.account


interface AccountManager{

    object FinishCallback

    interface ProcessResponse{
        val url: String
        suspend fun pastePinAction(pin: String): FinishCallback
    }

    fun isUserLogged() : Boolean

    suspend fun startAuthorizationProcess(): ProcessResponse
}