package com.task.project.utils

import android.content.Context
import android.net.ConnectivityManager
import com.task.project.R
import com.task.project.model.Location

object InternetConnection {
    var locations: List<Location> = ArrayList()

    val colors = arrayOf("#ff2299", "#225566", "#000033", "#110033", "#220033", "#440033", "#770033", "#880033", "#bb0033", "#ddcc00",
            "#55ff66", "#41bc66", "#156286", "#0af7fa", "#663377", "#551122", "#9c1a24", "#2f5982", "#b3002d", "#808080", "#000080", "#C665C8", "#CA40CD", "#8E2B91", "#d3c29c", "#776688", "#6195ed", "#BB0033", "#FFFF00", "#00FFFF", "#FF0000", "#808000", "#00FF00", "#000080", "#454545", "#0000FF", "#C0C0C0", "#808000", "#000000", "#FFFFFF")

    val linkImages = arrayOf(R.drawable.img_test,R.drawable.img_1,R.drawable.img_2,R.drawable.img_3)

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    fun checkConnection(context: Context): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
    }
}