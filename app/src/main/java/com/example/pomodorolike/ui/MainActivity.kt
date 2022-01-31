package com.example.pomodorolike.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment

import com.example.pomodorolike.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBar()

    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            this.window.statusBarColor =
                ContextCompat.getColor(this, R.color.white)
        }
    }

    override fun onBackPressed() {
        val settingsPageFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.let {
                NavHostFragment.findNavController(
                    it
                ).currentDestination?.id
            }

        if (settingsPageFragment == R.id.settingsPageFragment) {
            super.onBackPressed()
        } else {
            moveTaskToBack(true)
        }
    }
}