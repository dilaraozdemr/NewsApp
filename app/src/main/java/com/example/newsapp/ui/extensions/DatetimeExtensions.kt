package com.example.newsapp.ui.extensions

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale


fun String.toLocalDateTime(): LocalDateTime? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        LocalDateTime.parse(this, formatter)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun LocalDateTime.format(pattern: String = "dd/MM/yyyy HH:mm", locale: Locale = Locale.getDefault()): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
    return this.format(formatter)
}
