package com.devdk.melody.util

object LongToMin {
    fun longToMin(long: Long): String {
        val minutes = long / 1000 / 60
        val seconds = long / 1000 % 60
        return "$minutes:$seconds sec"
    }
}