package es.jgalcolea.axpejorgegil.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {
    fun parseDate(dateToFormat: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(dateToFormat)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}