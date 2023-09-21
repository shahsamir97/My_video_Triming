package com.mdshahsamir.myvideotriming

import android.util.Log

fun reverseCustomRangeMapping(
    mappedValue: Int,
    minValue: Int,
    maxValue: Int,
    customMax: Int
): Int {

    return (mappedValue / customMax) * (maxValue - minValue) + minValue
}

fun mapToCustomRange(
    number: Float,
    minValue: Float,
    maxValue: Float,
    customMax: Float
): Float {
    val clampedNumber =
        if (number < minValue) minValue else if (number > maxValue) maxValue else number

    return customMax * (clampedNumber - minValue) / (maxValue - minValue)
}

fun convertTimeFormat(second: Float): Triple<Float, Float, Float> {
    val hours = second / 3600
    val minutes = (second % 3600) / 60
    val remainingSeconds = second % 60
    return Triple(hours, minutes, remainingSeconds)
}

fun getDisplayableTimeFormat(second: Float): CharSequence? {
    val (hours, minutes, seconds) = convertTimeFormat(second)
    var outputString = ""

    if (hours != 0F)
        outputString += "${hours.toInt()} hours"
    if (minutes != 0F)
        outputString += " ${minutes.toInt()} minutes"

    return "$outputString ${seconds.toInt()} seconds"
}

fun displayLog(message: String) {
    Log.i("Lol", message)
}