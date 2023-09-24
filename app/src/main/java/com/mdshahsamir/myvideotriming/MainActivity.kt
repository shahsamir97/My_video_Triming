package com.mdshahsamir.myvideotriming

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mdshahsamir.myvideotriming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.simpleName
    private var frameLayoutWidth = 0
    private val CONTENT_TIME = 5000F
    private var isDragging = false
    var diff = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var initialX: Float = 0f
        var initialTouchX: Float = 0f

        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            updateDuration()
        }

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
                            val minX = 0f
                            if (newX >= minX && newX <= maxX && newX < binding.rightBar.x - binding.rightBar.width * 2) {
                                view.x = newX
                                binding.rightBarText.text = "LeftBar Pos: " + view.x.toString()
                                updateDuration()
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
                        val deltaX = event.rawX - initialTouchX
                        val newX = initialX + deltaX

                        val maxX = (view.parent as View).width - view.width
                        val minX = 0f + view.width
                        if (newX >= minX && newX <= maxX && newX - view.width * 2 > binding.leftBar.x) {
                            view.x = newX
                            binding.rightBarText.text = "RightBar Pos: " + view.x.toString()
                            updateDuration()
                        }
                    }
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
        val trimBarsWidth = binding.leftBar.width
        diff = (binding.rightBar.x - binding.leftBar.x) - trimBarsWidth
        val duration =
            mapToCustomRange(diff, 0F, binding.frameLayout.width.toFloat(), CONTENT_TIME.toFloat())
        binding.textView.text = getDisplayableTimeFormat(duration)
    }
}