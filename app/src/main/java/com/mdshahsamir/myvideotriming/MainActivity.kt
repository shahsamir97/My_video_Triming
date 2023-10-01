package com.mdshahsamir.myvideotriming

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.exoplayer.ExoPlayer
import com.mdshahsamir.myvideotriming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var frameLayoutWidth = 0
    private val CONTENT_TIME = 2000F
    private val MIN_TIME_LIMIT = 20F
    private var isDragging = false
    var diff = 0F
    var duration = 0F

    val player by lazy { ExoPlayer.Builder(this).build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var initialX = 0f
        var initialTouchX = 0f

        binding.leftBar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                frameLayoutWidth = binding.frameLayout.width

                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = view.x
                        initialTouchX = event.rawX
                        isDragging = true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (isDragging) {
                            val deltaX = event.rawX - initialTouchX
                            val newX = initialX + deltaX

                            val maxX = (view.parent as View).width - (view.width * 2)
                            val minX = 0F
                            if (newX >= minX && newX <= maxX && newX < binding.rightBar.x - binding.rightBar.width) {
                                updateDuration()
                                if (MIN_TIME_LIMIT < duration)
                                    view.x = newX
                                else if (newX < view.x) {
                                    view.x = newX
                                }
                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> isDragging = false
                }

                return true
            }
        })

        binding.rightBar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                frameLayoutWidth = binding.frameLayout.width
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = view.x
                        initialTouchX = event.rawX
                        isDragging = true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        if (isDragging) {
                            val deltaX = event.rawX - initialTouchX
                            val newX = initialX + deltaX

                            val maxX = (view.parent as View).width - view.width
                            val minX = 0F + view.width
                            if (newX >= minX && newX <= maxX && newX - view.width > binding.leftBar.x) {
                                updateDuration()
                                if (MIN_TIME_LIMIT < duration)
                                    view.x = newX
                                else if (newX > view.x) {
                                    view.x = newX
                                }
                            }
                        }
                    }
                    MotionEvent.ACTION_UP ->  isDragging = false
                }

                return true
            }
        })

        binding.textView.text = getDisplayableTimeFormat(CONTENT_TIME)
    }

    override fun onResume() {
        super.onResume()
        updateDuration()
    }

    fun updateDuration() {
        val trimBarsWidth = binding.leftBar.width + 1
        diff = (binding.rightBar.x - binding.leftBar.x) - trimBarsWidth
        duration = timeRange(diff)
        Log.i(this::class.simpleName, duration.toString())

        if (duration < MIN_TIME_LIMIT) {
            duration = MIN_TIME_LIMIT
        }

        binding.textView.text = getDisplayableTimeFormat(duration)
    }

    fun timeRange(diff: Float): Float {
        return mapToCustomRange(diff, 0F, binding.frameLayout.width.toFloat(), CONTENT_TIME)
    }
}