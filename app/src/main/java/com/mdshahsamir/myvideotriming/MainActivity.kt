package com.mdshahsamir.myvideotriming

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mdshahsamir.myvideotriming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var leftBarInitialWidth: Int = 0
    private var rightBarInitialWidth: Int = 0
    private var isDragging = false
    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        leftBarInitialWidth = binding.leftBar.width
        rightBarInitialWidth = binding.rightBar.width


        binding.leftBar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> isDragging = true

                    MotionEvent.ACTION_MOVE -> if (isDragging) {
                        if (binding.rightBar.x > event.rawX + view.width)
                            view.x = event.rawX
                    }

                    MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> isDragging = false
                }

                return true
            }
        })

        binding.rightBar.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> isDragging = true

                    MotionEvent.ACTION_MOVE -> if (isDragging) {
                        if (binding.leftBar.x < event.rawX - view.width)
                        view.x = event.rawX
                    }

                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> isDragging = false
                }

                return true
            }
        })
    }


    fun convertTimeFormat(second: Int): Triple<Int, Int, Int> {
        val hours = second / 3600
        val minutes = (second % 3600) / 60
        val remainingSeconds = second % 60
        return Triple(hours, minutes, remainingSeconds)
    }

    fun getDisplayableTimeFormat(second: Int): CharSequence? {
        val (hours, minutes, seconds) = convertTimeFormat(second)
        var outputString = ""

        if (hours != 0)
            outputString += "$hours hours"
        if (minutes != 0)
            outputString += " $minutes minutes"

        return "$outputString $seconds seconds"
    }

    fun displayLog(message: String) {
        Log.i(TAG, message)
    }
}