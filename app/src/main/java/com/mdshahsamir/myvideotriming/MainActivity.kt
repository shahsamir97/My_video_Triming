package com.mdshahsamir.myvideotriming

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mdshahsamir.myvideotriming.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.simpleName

    private var frameLayoutWidth = 0
    private val CONTENT_TIME = 2000F
    private val MIN_TIME_LIMIT = 5F
    private var isDragging = false

    var diff = 0F
    var duration = 0F
    var startTime = 0F
    var endTime = CONTENT_TIME - MIN_TIME_LIMIT

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
                        binding.topSlider.visibility = View.VISIBLE
                        binding.zoomSlider.x = binding.leftBar.x
                    }

                    MotionEvent.ACTION_MOVE -> {
                        if (isDragging) {
                            if (event.y < 0) {
                                handleDragOnZoomedTrack(event, initialTouchX, initialX)
                            }
                            else {
                                val deltaX = event.rawX - initialTouchX
                                val newX = initialX + deltaX

                                val maxX = (view.parent as View).width - (view.width * 2)
                                val minX = 0F

                                if (newX >= minX && newX <= maxX && newX < binding.rightBar.x - binding.rightBar.width - 1) {
                                    if (MIN_TIME_LIMIT < duration) {
                                        view.x = newX
                                    } else if (newX < view.x) {
                                        view.x = newX
                                    }
                                    updateDuration()
                                } else if (newX < minX) {
                                    view.x = 0F
                                    updateDuration()
                                } else if (newX > binding.rightBar.x - binding.rightBar.width) {
                                    view.x = binding.rightBar.x - binding.rightBar.width - 1
                                    updateDuration()
                                }

                                calculateEachSliderTime(
                                    view.x - 1,
                                    binding.leftbarTime,
                                    "Left Slider Time : "
                                )
                            }
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        isDragging = false
                        binding.topSlider.visibility = View.INVISIBLE
                        binding.zoomSlider.x = 0F
                    }
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
                        binding.topSlider.visibility = View.VISIBLE
                        binding.zoomSlider.x = binding.rightBar.x
                    }

                    MotionEvent.ACTION_MOVE -> {
                        if (isDragging) {
                            if (event.y < 0) {
                                handleDragOnZoomedTrack(event, initialTouchX, initialX)
                            }
                            else {
                                val deltaX = event.rawX - initialTouchX
                                val newX = initialX + deltaX

                                val maxX = (view.parent as View).width - view.width
                                val minX = 0F + view.width

                                if (newX >= minX && newX <= maxX && newX - view.width > binding.leftBar.x + 1) {
                                    if (MIN_TIME_LIMIT < duration) {
                                        view.x = newX
                                    } else if (newX > view.x) {
                                        view.x = newX
                                    }
                                    updateDuration()
                                } else if (newX > maxX) {
                                    view.x = maxX.toFloat()
                                    updateDuration()
                                } else if (newX - view.width < binding.leftBar.x) {
                                    view.x = binding.leftBar.x + view.width + 1
                                    updateDuration()
                                }

                                calculateEachSliderTime(
                                    view.x - view.width + 1,
                                    binding.rightbarTime,
                                    "Right Slider Time : "
                                )
                            }
                            }
                    }

                    MotionEvent.ACTION_UP -> {
                        isDragging = false
                        binding.topSlider.visibility = View.INVISIBLE
                        binding.zoomSlider.x = 0F
                    }
                }

                return true
            }
        })
    }

    private fun handleDragOnZoomedTrack(event: MotionEvent, initTouchX: Float, initX: Float) {
        val deltaX = event.rawX - initTouchX
        val newX = initX + deltaX

        val maxX = binding.topSlider.width - binding.zoomSlider.width
        val minX = binding.topSlider.x + binding.zoomSlider.width

        if (newX <= maxX && newX >= minX) {
            binding.zoomSlider.x = newX
        }
    }

    private fun calculateEachSliderTime(positionX: Float, textView: TextView, message: String) {
        val seconds = timeRange(positionX)

        if (textView.id == R.id.leftbarTime) {
            startTime = seconds
        } else {
            endTime = seconds
        }

        textView.text = message + getDisplayableTimeFormat(seconds)
    }

    override fun onResume() {
        super.onResume()
        updateDuration()
    }

    fun updateDuration() {
        val trimBarsWidth = binding.leftBar.width
        diff = (binding.rightBar.x - binding.leftBar.x) - trimBarsWidth
        duration = timeRange(diff)

        if (duration < MIN_TIME_LIMIT) {
            duration = MIN_TIME_LIMIT
        }

        binding.textView.text = getDisplayableTimeFormat(duration)
    }

    fun timeRange(diff: Float): Float {
        return mapToCustomRange(
            number = diff,
            minValue = 0F,
            maxValue = binding.frameLayout.width.toFloat() - binding.rightBar.width * 2,
            customMax = CONTENT_TIME
        )
    }
}
