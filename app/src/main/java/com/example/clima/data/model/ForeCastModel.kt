package com.example.clima.data.model

import com.example.clima.data.source.*
import com.example.clima.data.source.remote.dto.ForeCastDTO
import com.example.clima.domain.model.ForeCastDomain


data class ForeCastModel(
    var date: String,
    var temperature: String,
    var city: String
) {
    companion object {
        fun ForeCastRemoteMapper(dto: ForeCastDTO): List<ForeCastDomain> {
            val list = mutableListOf<ForeCastDomain>()
            val city = dto.city?.name
            val map = dto.list?.groupBy {
                it?.dtTxt?.parseToDate(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)
                    ?.formatToPattern(DATE_FORMAT_DD_MM_YYYY)

            }
            map?.keys?.forEachIndexed { index, key ->
                if (index == 5) {
                    return@forEachIndexed
                }
                val foreCast = map[key]?.get(0)
                list.add(
                    ForeCastDomain(
                        date = foreCast?.dtTxt?.parseToDate(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)
                            ?.formatToPattern() ?: "",
                        temperature = foreCast?.main?.temp?.kelvinToCelsius() ?: "",
                        city = city ?: ""
                    )
                )
            }
            return list
        }
    }
}
