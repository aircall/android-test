package io.aircall.android.domain.usecase

sealed class Result<out Data, out Error> {
    data class Data<out Data>(val data: Data) : Result<Data, Nothing>()
    data class Error<out Error>(val error: Error) : Result<Nothing, Error>()
    object Loading : Result<Nothing, Nothing>()
}