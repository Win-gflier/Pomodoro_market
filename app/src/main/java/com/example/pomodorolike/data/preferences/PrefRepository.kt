package com.example.pomodorolike.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.pomodorolike.data.*

class PrefRepository(
    context: Context
) {


    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = preference.edit()


    //Focus Time Setters

    fun setFocusTimerLengthHours(hours:Int){
        FOCUS_TIMER_LENGTH_HOURS.put(hours)
    }
    fun setFocusTimerLengthMinutes(minutes:Int){
        FOCUS_TIMER_LENGTH_MINUTES.put(minutes)
    }
    fun setFocusTimerLengthSeconds(seconds:Int){
        FOCUS_TIMER_LENGTH_SECONDS.put(seconds)
    }
    fun setFocusTimerLengthMSeconds(mSeconds:Int){
        FOCUS_TIMER_LENGTH_MILLI_SECONDS.put(mSeconds)
    }

    //Focus Time Getters

    fun getFocusTimerLengthHours() = FOCUS_TIMER_LENGTH_HOURS.getInt()

    fun getFocusTimerLengthMinutes() = FOCUS_TIMER_LENGTH_MINUTES.getInt()

    fun getFocusTimerLengthSeconds() = FOCUS_TIMER_LENGTH_SECONDS.getInt()

    fun getFocusTimerLengthMSeconds() = FOCUS_TIMER_LENGTH_MILLI_SECONDS.getInt()


    //Short Break Time Setters

    fun setShortBreakTimerLengthHours(hours:Int){
        SHORT_BREAK_TIMER_LENGTH_HOURS.put(hours)
    }
    fun setShortBreakTimerLengthMinutes(minutes:Int){
        SHORT_BREAK_TIMER_LENGTH_MINUTES.put(minutes)
    }
    fun setShortBreakTimerLengthSeconds(seconds:Int){
        SHORT_BREAK_TIMER_LENGTH_SECONDS.put(seconds)
    }
    fun setShortBreakTimerLengthMSeconds(mSeconds:Int){
        SHORT_BREAK_TIMER_LENGTH_MILLI_SECONDS.put(mSeconds)
    }

    //Short Break Time Getters
    fun getShortBreakTimerLengthHours() = SHORT_BREAK_TIMER_LENGTH_HOURS.getInt()

    fun getShortBreakTimerLengthMinutes() = SHORT_BREAK_TIMER_LENGTH_MINUTES.getInt()

    fun getShortBreakTimerLengthSeconds() = SHORT_BREAK_TIMER_LENGTH_SECONDS.getInt()

    fun getShortBreakTimerLengthMSeconds() = SHORT_BREAK_TIMER_LENGTH_MILLI_SECONDS.getInt()

    //Long Break Time Setters

    fun setLongBreakTimerLengthHours(hours:Int){
        LONG_BREAK_TIMER_LENGTH_HOURS.put(hours)
    }
    fun setLongBreakTimerLengthMinutes(minutes:Int){
        LONG_BREAK_TIMER_LENGTH_MINUTES.put(minutes)
    }
    fun setLongBreakTimerLengthSeconds(seconds:Int){
        LONG_BREAK_TIMER_LENGTH_SECONDS.put(seconds)
    }
    fun setLongBreakTimerLengthMSeconds(mSeconds:Int){
        LONG_BREAK_TIMER_LENGTH_MILLI_SECONDS.put(mSeconds)
    }

    //Short Break Time Getters
    fun getLongBreakTimerLengthHours() = LONG_BREAK_TIMER_LENGTH_HOURS.getInt()

    fun getLongBreakTimerLengthMinutes() = LONG_BREAK_TIMER_LENGTH_MINUTES.getInt()

    fun getLongBreakTimerLengthSeconds() = LONG_BREAK_TIMER_LENGTH_SECONDS.getInt()

    fun getLongBreakTimerLengthMSeconds() = LONG_BREAK_TIMER_LENGTH_MILLI_SECONDS.getInt()


    //Number of Cycles Setters
    fun setNumberOfCycles(cycleCount:Int){
        NUMBER_OF_CYCLES.put(cycleCount)
    }

    //Number of Cycles Getters
    fun getNumberOfCycles() = NUMBER_OF_CYCLES.getInt()

    //Auto Start Setters
    fun setAutoStartBreaks(bool:Boolean){
        AUTO_START_BREAKS.put(bool)
    }
    fun setAutoStartWorkTime(bool:Boolean){
        AUTO_START_WORK_TIME.put(bool)
    }

    //Auto Start Getters
    fun getAutoStartBreaks() = AUTO_START_BREAKS.getBoolean()
    fun getAutoStartWorkTime() = AUTO_START_WORK_TIME.getBoolean()






    //KOTLIN EXTENSION FUNCTIONS
    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = preference.getLong(this, 0)

    private fun String.getInt() = preference.getInt(this, 0)

    private fun String.getString() = preference.getString(this, "")!!

    private fun String.getBoolean() = preference.getBoolean(this, false)
}