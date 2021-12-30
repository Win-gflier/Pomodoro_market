package com.example.pomodorolike.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.pomodorolike.R
import com.example.pomodorolike.ui.main_page.MainPageFragment
import com.example.pomodorolike.ui.main_page.MainPageViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}