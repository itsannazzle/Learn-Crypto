package com.nextint.learncrypto.app.features.utils

import android.text.Html
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

    fun String.convertStringToDate() : String?
    {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("d MMMM yyyy HH:mm", Locale.US)

        val date: Date = inputFormat.parse(this) ?: Date()

        return outputFormat.format(date)
    }

    fun String.convertDateWithFormat(stringDateFormat : String) : Date?
    {
        val stringDateFormat =  SimpleDateFormat(stringDateFormat, Locale.ROOT)
        return stringDateFormat.parse(this)
    }

fun String.removeHTMLFormat() : String
{
    val sanitizedText = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    return sanitizedText


        .replace("<.*?>".toRegex(), "")
}

fun Date.convertDateToStingPreviewSimple() : String
{
    val stringDateFormat = "dd MMMM yyyy"
    val simpleDateFormat = SimpleDateFormat(stringDateFormat, Locale.getDefault())
    return simpleDateFormat.format(this)
}
