package com.amishgarg.wartube

import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*

class TimeDisplay(timestamp: Long) {

    val timestamp: Long = timestamp
    val current : Long = System.currentTimeMillis()
    val distanceMillis : Long = current-timestamp


    val seconds = (distanceMillis / 1000).toDouble()
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val years = days / 365

    var time : String = ""

    fun getDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(timestamp))
    }

    fun getTimeDisplay() : String
    {
        if(days > 4)
        {
            return getDate()
        }
        else {
            if (seconds < 45)
                time = "${Math.round(seconds)} secs"
            else if (seconds < 90)
                time = "a minute"
            else if (minutes < 45)
                time = "${Math.round(minutes)} mins"
            else if (minutes < 90)
                time = "an hour"
            else if (hours < 24)
                time = "${Math.round(hours)} hrs"
            else if (hours < 48)
                time = "a day"
            else if (days < 4)
                time = "${Math.round(days)} days"
        }
        return time+" ago"
    }

}