package br.com.taxivix.util

import android.content.Context

object SharedPreferencesManager {
    fun getInstance(context: Context) = context.getSharedPreferences("", Context.MODE_PRIVATE)
}