package com.example.myfirstgame

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
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
        var lec = false

        binding.platform.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x
                    lec = true
                    binding.platform.text = ""
                    binding.cat.visibility = View.VISIBLE
                }

                MotionEvent.ACTION_MOVE -> {
                    val xMoved = event.x
                    val distanceX = xMoved - xDown
                    binding.platform.x += distanceX
                }
            }
            true
        }

        var pkt = 0
        var zycia = 10
        var speed = 1f

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (lec) {
                    binding.cat.y += 1 * speed
                    if (binding.cat.y > binding.platform.y - 200f) {
                        if ((binding.platform.x - 50f..binding.platform.x + 250f).contains(binding.cat.x)) {
                            pkt++
                        } else zycia--
                        speed += 0.1f

                        if (zycia <= 0) {
                            runOnUiThread {
                                binding.cat.visibility = View.INVISIBLE
                                binding.endText.visibility = View.VISIBLE
                            }
                            lec = false
                        }

                        binding.cat.y = 0f
                        runOnUiThread {
                            binding.points.text = "Punkty: $pkt\nŻycia: $zycia"
                        }
                    }
                }
            }
        }, 0, 2)

        var lewoPrawo = Random.nextBoolean()
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (lec) {
                    for (i in 1..50) {
                        if (lewoPrawo) {
                            if (binding.cat.x > -30f) binding.cat.x -= 1 * speed
                        } else if (binding.cat.x < binding.right.x - 150f) binding.cat.x += 1 * speed
                        Thread.sleep(5)
                    }
                    lewoPrawo = Random.nextBoolean()
                }
            }
        }, 0, 250)
    }
}