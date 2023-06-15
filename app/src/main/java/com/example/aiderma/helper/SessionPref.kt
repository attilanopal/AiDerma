package com.example.aiderma.helper

import android.content.Context
import com.example.aiderma.api.response.LoginResponse

class SessionPref(context: Context) {
    companion object {
        const val PREFS_NAME = "login_pref"
        const val IS_LOGIN = "islogin"
        const val TOKEN = "token"
        val TAG = "SESSIONPREF"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun loginUser(data: LoginResponse) {
        val editor = preferences.edit()
        editor.putString(TOKEN, data.token)
        editor.putBoolean(IS_LOGIN, true)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGIN, false)
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN, null)
    }

    // For user to log out
    fun clearSession() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}
