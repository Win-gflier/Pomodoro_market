package com.example.pomodorolike.ui.main_page

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainPageViewModel : ViewModel() {
    private lateinit var timer: CountDownTimer
    private var _timerLengthSecond = MutableLiveData<Long>()
    private var _mSecondsRemaining = MutableLiveData<Long>()
    private var _timerState = MutableLiveData<TimerState>()
    private var _completeCycleCount = MutableLiveData(0)

    enum class TimerState {
        Stopped, Paused, Running
    }


    fun completeCycleCount(): LiveData<Int> {
        return _completeCycleCount
    }

    fun setCompletedCycleCount(i: Int) {
            _completeCycleCount.value = _completeCycleCount.value!! + i
    }


    fun mSecondsRemaining(): LiveData<Long> {
        return _mSecondsRemaining
    }

    fun timerState(): LiveData<TimerState> {
        return _timerState
    }


    fun startTimer(time: Long) {
        _timerState.value = TimerState.Running
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                _mSecondsRemaining.value = p0
            }
            override fun onFinish() = stopTimer()
        }.start()
    }


    fun pauseTimer() {
        _timerState.value = TimerState.Paused
        timer.cancel()
    }

    fun stopTimer() {
        _timerState.value = TimerState.Stopped
        timer.cancel()
    }

}