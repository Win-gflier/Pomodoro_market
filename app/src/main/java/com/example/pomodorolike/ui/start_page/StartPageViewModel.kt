package com.example.pomodorolike.ui.start_page

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartPageViewModel : ViewModel() {

    val focusWantsToOpen: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val shortBreakWantsToOpen: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val longBreakWantsToOpen: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val cyclePickerWantsToOpen: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }



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
    }}