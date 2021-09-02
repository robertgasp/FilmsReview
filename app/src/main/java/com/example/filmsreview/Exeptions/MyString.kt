package com.example.filmsreview.Exeptions

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

class MyString {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var activity:Activity
        fun View.getTextFromRes(view: View, textResPath: Int, duration: Int) {
            Snackbar.make(view, activity.getString(textResPath), duration)
                .show()
        }
    }
}