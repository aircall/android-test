package io.aircall.android.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

abstract class UseCase <out Success, out Error> {
    abstract suspend fun run(): Flow<Result<Success, Error>>

    suspend operator fun invoke(): Flow<Result<Success, Error>> {
        return withContext(Dispatchers.IO) { run() }
    }
}