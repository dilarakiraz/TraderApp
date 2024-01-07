package com.dilarakiraz.traderapp.common


/**
 * Created on 6.01.2024
 * @author Dilara Kiraz
 */

sealed class Resource<out T> {
    data class Success<out T>(val data: T?, val accountNumber: String?) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    class Loading<T> : Resource<T>()
}



