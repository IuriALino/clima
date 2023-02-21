package com.example.clima.data.source.remote.mapper

interface RemoteMapper<DTO, ENTITY, DOMAIN> {
    fun toEntity(dto: DTO): ENTITY?
    fun toDomain(dto: DTO): DOMAIN?
}