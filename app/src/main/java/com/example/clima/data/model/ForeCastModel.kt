package com.example.clima.data.model

import com.example.clima.data.source.*
import com.example.clima.data.source.retrofit.response.ForeCastResponse

data class ForeCastModel(
    var date: String,
    var temperature: String
) {
    companion object {
        fun fromResponse(foreCastResponse: ForeCastResponse): List<ForeCastModel> {
            val list = mutableListOf<ForeCastModel>()
            val map = foreCastResponse.list.groupBy {
                it.dtTxt.parseToDate(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)
                    ?.formatToPattern(DATE_FORMAT_DD_MM_YYYY)
            }
            map.keys.forEach {
                val foreCast = map[it]?.get(0)
                list.add(
                    ForeCastModel(
                        foreCast?.dtTxt?.parseToDate(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)
                            ?.formatToPattern() ?: "", foreCast?.main?.temp?.kelvinToCelsius() ?: ""
                    )
                )
            }
            return list
        }
    }
}
