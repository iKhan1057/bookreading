package com.bookreaderapp.utils

import android.icu.text.DateFormat
import android.os.Build
import com.google.firebase.Timestamp

fun formatDate(timestamp: Timestamp): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0]
    } else {
        TODO("VERSION.SDK_INT < N")
    } // March 12
}