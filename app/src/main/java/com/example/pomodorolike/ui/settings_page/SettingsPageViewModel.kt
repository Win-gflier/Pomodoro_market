package com.example.pomodorolike.ui.settings_page

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel

class SettingsPageViewModel : ViewModel() {


    fun updateOrangeTextsInt(input: Int, stringInput: String, view: TextView) {
        if (input == 0) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
            view.text = stringInput
        }
    }

    fun updateOrangeTextsLong(input: Long, stringInput: String, view: TextView) {
        if (input == 0L) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
            view.text = stringInput
        }
    }


}