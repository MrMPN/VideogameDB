package com.mrmpn.videogamedb.data.remote


sealed interface ApiResponse<out T> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T>

    sealed interface Error : ApiResponse<Nothing> {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpError(val code: Int, val errorBody: String?, val e: Exception) : Error

        /**
         * Represent IOExceptions and connectivity issues.
         */
        data class NetworkError(val e: Exception) : Error

        /**
         * Represent SerializationExceptions.
         */
        data class SerializationError(val e: Exception) : Error

        /**
         * Represent unknown errors.
         */
        data class UnknownError(val e: Exception) : Error
    }
}