package com.example.covidcasesdata.utils

import android.icu.text.RelativeDateTimeFormatter
import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class HelperUtil {
    companion object {
        fun convertToINS(input: String): String {
            val sbInput: StringBuilder = StringBuilder(input);
            // Find the length of the
            // sbInput
            var len: Int = sbInput.length
            // Removing all the separators(, )
            // from the sbInput
            var i: Int = 0
            while (i < len) {
                if (sbInput[i] == ',') {
                    sbInput.deleteCharAt(i);
                    len--
                    i--
                } else if (sbInput[i] == ' ') {
                    sbInput.deleteCharAt(i);
                    len--
                    i--
                } else {
                    i++
                }
            }
            // Reverse the sbInput
            val sbInputReverse = sbInput.reverse()

            // Declaring the output
            val output = java.lang.StringBuilder()

            // Process the sbInput
            for (i in 0 until len) {
                // Add a separator(, ) after the
                // third number
                if (i == 2) {
                    output.append(sbInputReverse[i])
                    output.append(",")
                } else if (i > 2 && i % 2 == 0 && i + 1 < len) {
                    output.append(sbInputReverse[i])
                    output.append(",")
                } else {
                    output.append(sbInputReverse[i])
                }
            }
            // Reverse the output
            val reverseOutput = output.reverse()
            // Return the output string back
            // to the main function
            return reverseOutput.toString()
        }

        fun YMDtoDayMonth(string_date: String): String{
            val f = SimpleDateFormat("yyyy-MM-dd")
            return try {
                val d: Date = f.parse(string_date)
                val milliseconds: Long = d.time
                val c = Calendar.getInstance()
                val mayBeToday: Long = milliseconds + 86400000
                if (DateUtils.isToday(mayBeToday)) return "Yesterday"
                //Set time in milliseconds
                c.timeInMillis = milliseconds
                val month = String.format(Locale.US,"%tB",c)
                val mDay = c[Calendar.DAY_OF_MONTH]
                "$mDay ${month.subSequence(0,3)}"
            } catch (e: ParseException) {
                e.printStackTrace()
                "DD-MMM"
            }
        }
    }


}