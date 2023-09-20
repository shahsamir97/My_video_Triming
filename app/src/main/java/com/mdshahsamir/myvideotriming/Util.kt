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
    number: Int,
    minValue: Int,
    maxValue: Int,
    customMax: Int
): Int {
    val clampedNumber =
        if (number < minValue) minValue else if (number > maxValue) maxValue else number

    return customMax * (clampedNumber - minValue) / (maxValue - minValue)
}

fun convertTimeFormat(second: Int): Triple<Int, Int, Int> {
    val hours = second / 3600
    val minutes = (second % 3600) / 60
    val remainingSeconds = second % 60
    return Triple(hours, minutes, remainingSeconds)
}

fun getDisplayableTimeFormat(second: Int): CharSequence? {
    val (hours, minutes, seconds) = convertTimeFormat(second)
    var outputString = ""

    if (hours != 0)
        outputString += "$hours hours"
    if (minutes != 0)
        outputString += " $minutes minutes"

    return "$outputString $seconds seconds"
}

fun displayLog(message: String) {
    Log.i("Lol", message)
}