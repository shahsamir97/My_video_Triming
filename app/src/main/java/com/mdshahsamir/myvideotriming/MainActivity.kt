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
                                updateDuration()
                            }
                        }
                    }
                }

                return true
            }
        })
    }

    fun updateDuration() {
        val diff = (binding.rightBar.x - binding.leftBar.x).toInt()
        val duration = mapToCustomRange(diff, 0, binding.frameLayout.width, 5000)
        binding.textView.text = getDisplayableTimeFormat(duration)

    }
}