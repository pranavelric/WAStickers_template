package com.template.samplestickerapp.main.utils

import android.content.Context
import android.content.SharedPreferences

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MySharedPrefrences @Inject constructor(@ApplicationContext context: Context) {

    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(Constants.SHARED_PREF, 0)
    }

    private var editor = sp.edit()


    fun cleaSession() {
        editor.clear()
        editor.commit()
    }

    fun setIsSecondDownloadCount(count: Int) {
        editor.putInt(Constants.SECOND_DOWNLOAD, count)
        editor.commit()
    }

    fun getSecondCount(): Int {
        return sp.getInt(Constants.SECOND_DOWNLOAD, 0)
    }


}