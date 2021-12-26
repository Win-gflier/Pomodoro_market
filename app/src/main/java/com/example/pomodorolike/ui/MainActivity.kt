package com.example.pomodorolike.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pomodorolike.R
import com.example.pomodorolike.ui.main_page.MainPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainPageFragment = MainPageFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, mainPageFragment)
            commit()
        }
    }


}