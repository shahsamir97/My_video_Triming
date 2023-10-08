package com.mdshahsamir.myvideotriming

import android.util.Log

fun mapToCustomRange(
    number: Float,
    minValue: Float,
    maxValue: Float,
    customMax: Float
): Float {
    val clampedNumber =
        if (number <= minValue) minValue else if (number >= maxValue) maxValue else number

    return customMax * (clampedNumber - minValue) / (maxValue - minValue)
}

fun scaleValueToRange(value: Float, minRange: Float, maxRange: Float): Float {
    // Check if the value is already within the desired range
    if (value < minRange) {
        return minRange
    } else if (value > maxRange) {
        return maxRange
    } else {
        // Scale the value to the desired range
        val scaledValue = (value - minRange) / (maxRange - minRange)
        return scaledValue
    }
}

fun convertTimeFormat(second: Float): Triple<Float, Float, Float> {
    val hours = second / 3600
    val minutes = (second % 3600) / 60
    val remainingSeconds = second % 60
    return Triple(hours, minutes, remainingSeconds)
}

fun getDisplayableTimeFormat(second: Float): CharSequence {
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