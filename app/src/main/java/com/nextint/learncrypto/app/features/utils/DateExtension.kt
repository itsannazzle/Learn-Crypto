package com.nextint.learncrypto.app.features.utils

import java.text.SimpleDateFormat
import java.util.*

    fun String.convertStringToDate() : Date?
    {
        return convertDateWithFormat("yyyy-MM-dd")
    }

    fun String.convertDateWithFormat(stringDateFormat : String) : Date?
    {
        val stringDateFormat =  SimpleDateFormat(stringDateFormat, Locale.ROOT)
        return stringDateFormat.parse(this)
    }

fun Date.convertDateToStingPreviewSimple() : String
{
    val stringDateFormat = "dd MMMM yyyy"
    val simpleDateFormat = SimpleDateFormat(stringDateFormat, Locale.getDefault())
    return simpleDateFormat.format(this)
}
