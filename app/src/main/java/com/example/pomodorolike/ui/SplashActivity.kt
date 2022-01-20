package com.example.pomodorolike.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.pomodorolike.R
import com.bumptech.glide.load.engine.GlideException
import android.graphics.drawable.Drawable
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


class SplashActivity : AppCompatActivity() {
    lateinit var imageView1: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imageView1 = findViewById(R.id.iv_gif)
        setStatusBar()
        loadImageGif()

    }
    private fun goToMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }, 1000)
    }
    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            this.window.statusBarColor =
                ContextCompat.getColor(this, R.color.grey_very_light)
        }
    }
    private fun loadImageGif(){


        Glide
            .with(this)
            .asGif()
            .load(R.drawable.pomodoro_splash_screen)
            .apply(RequestOptions.diskCacheStrategyOf(
                DiskCacheStrategy.NONE))
            .listener(object : RequestListener<GifDrawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.setLoopCount(1)
                    resource?.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            super.onAnimationEnd(drawable)
                            goToMain()
                        }
                    })
                    return false
                }

            })
            .into(imageView1)
    }
}