package id.andreasdimas.traveloka.utils

import java.text.SimpleDateFormat
import java.util.*


const val DATE_FORMAT = "yyyy-MM-dd"
const val DATE_FORMAT2 = "dd MMMM yyyy"
const val DATE_FORMAT3 = "EEEE, dd MMM yyyy"

fun getTodayDate(): String {
    val cal = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    return simpleDateFormat.format(cal.time)
}

fun getTodayDateTimeDay(): String {
    val cal = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT3, Locale.getDefault())

    return simpleDateFormat.format(cal.time)
}


fun getDateString(date: Date): String {
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    return simpleDateFormat.format(date)
}

fun getDateStringFormatted(date: Date): String {
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT2, Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
