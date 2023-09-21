package com.mdshahsamir.myvideotriming

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mdshahsamir.myvideotriming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.simpleName
    private var frameLayoutWidth = 0
    private val CONTENT_TIME = 5000F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.leftBar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                frameLayoutWidth = binding.frameLayout.width

                when (event?.action) {
                    MotionEvent.ACTION_MOVE -> {
                        if (binding.rightBar.x > event.rawX + view.width) {
                            view.x = event.rawX
                            binding.leftBarText.text = "LeftBar Pos: " + view.x.toString()
                            updateDuration()
                        }
                    }
                }

                return true
            }
        })

        binding.rightBar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                frameLayoutWidth = binding.frameLayout.width
                when (event?.action) {
                    MotionEvent.ACTION_MOVE -> {
                        if (binding.leftBar.x < event.rawX - view.width) {
                            if (event.rawX < binding.frameLayout.width - view.width) {
                                view.x = event.rawX
                                binding.rightBarText.text = "RightBar Pos: " + view.x.toString()
                                updateDuration()
                            }
                        }
                    }
                }

                return true
            }
        })

        binding.textView.text = getDisplayableTimeFormat(CONTENT_TIME)
    }

    fun updateDuration() {
        val trimBarsWidth = binding.leftBar.width
        val diff = (binding.rightBar.x - binding.leftBar.x) - trimBarsWidth
        val duration = mapToCustomRange(diff, 0F, binding.frameLayout.width.toFloat(), CONTENT_TIME.toFloat())
        binding.textView.text = getDisplayableTimeFormat(duration)
    }
}