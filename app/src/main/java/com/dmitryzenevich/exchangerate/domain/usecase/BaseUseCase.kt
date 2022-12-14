package com.dmitryzenevich.exchangerate.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<out Type, in Params> where Type : Any? {

    abstract suspend fun run(params: Params): Type

    fun execute(params: Params): Flow<Type> = flow {
        emit(run(params))
    }
}