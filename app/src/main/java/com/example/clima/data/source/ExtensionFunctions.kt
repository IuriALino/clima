package com.example.clima.data.source
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

suspend fun <T> Response<T>?.defaultFailure(failure: (t: JsonObject) -> Unit) = withContext(
    Dispatchers.IO
) {
    val response = this@defaultFailure
    try {
        val json = response?.errorBody()?.string()
        val jsonObject = JsonParser().parse(json).asJsonObject
        jsonObject.addProperty("httpStatus", response?.code())
        failure(jsonObject)
    } catch (e: Exception) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(
            "message",
            "Ocorreu um erro no recebimento dos dados.\nPor favor tente novamente."
        )
        failure(jsonObject)
    }
}

suspend fun Throwable?.defaultError(failure: (JsonObject) -> Unit) = withContext(Dispatchers.IO) {
    val throwable = this@defaultError
    throwable?.let {
        try {
            val jsonObject = JsonObject()
            jsonObject.addProperty("message", it.getFormatedMessage())
            jsonObject.addProperty("localizedMessage", it.localizedMessage)
            jsonObject.addProperty("throwable", it.toString())
            failure(jsonObject)
        } catch (e: Exception) {
            val jsonObject = JsonObject()
            jsonObject.addProperty(
                "message",
                "Ocorreu um erro no recebimento dos dados.\nPor favor tente novamente."
            )
            failure(jsonObject)
        }
    }
}

fun Throwable.getFormatedMessage(): String {
    return when (this) {
        is ConnectException -> "Erro ao estabelecer conexão com o servidor"
        is UnknownHostException -> "Verifique sua conexão com a internet"
        else -> this.message ?: "Ocorreu um erro inesperado"
    }
}

const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
const val DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy"

fun String.parseToDate(
    pattern: String = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS,
    locale: Locale = Locale("pt", "BR"),
    isUTC: Boolean = false
): Date? {
    return try {
        val simpleDateFormat = SimpleDateFormat(
            pattern,
            locale
        )

        var date = simpleDateFormat.parse(this)
        if (isUTC) {
            date?.let {
                val calendar = getCalendarInstanceSPTimeZone()
                calendar.time = it
                calendar.add(Calendar.HOUR_OF_DAY, -3)
                date = calendar.time
            }
        }
        date
    } catch (ex: Exception) {
        null
    }
}

fun Date.formatToPattern(
    pattern: String = DATE_FORMAT_DD_MM_YYYY,
    locale: Locale = Locale("pt", "BR")
): String {
    val simpleDateFormat = SimpleDateFormat(
        pattern,
        locale
    )

    return simpleDateFormat.format(this)
}

fun getCalendarInstanceSPTimeZone(): Calendar {
    val timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    TimeZone.setDefault(timeZone)
    return GregorianCalendar.getInstance(timeZone)
}

fun Double.kelvinToCelsius() : String {
    val dec = DecimalFormat("###.##")
    val celsius = this - 273.15
    return "${dec.format(celsius)} ºC"
}
