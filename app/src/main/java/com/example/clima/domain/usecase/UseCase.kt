package com.example.clima.domain.usecase

interface UseCase<Param, Return> {
    suspend fun execute(param: Param): Return
}