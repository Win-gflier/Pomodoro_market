package com.example.pomodorolike.ui.rest_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RestPageViewModel : ViewModel() {
    var initialNumber = 0
    val _completeCycleCount: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

}