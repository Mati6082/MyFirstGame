package com.example.myfirstgame

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.example.myfirstgame.databinding.ActivityGameBinding
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var xDown = 0f

        binding.platform.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x
                }

                MotionEvent.ACTION_MOVE -> {
                    val xMoved = event.x
                    val distanceX = xMoved - xDown
                    binding.platform.x += distanceX
                }
            }
            true
        }

        var lec = true

        binding.platform.y = 1800f

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (lec) {
                    binding.cat.y += 10

                    if (binding.cat.y > binding.platform.y) lec = false
                }
            }
        }, 0, 5)
    }
}