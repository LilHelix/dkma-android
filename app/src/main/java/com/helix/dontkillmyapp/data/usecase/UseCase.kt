package com.helix.dontkillmyapp.data.usecase

import com.helix.dontkillmyapp.utils.Try

interface UseCase<T> {

    suspend fun execute() : Try<T>
}

