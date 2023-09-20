package com.mdshahsamir.myvideotriming

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mdshahsamir.myvideotriming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.leftBar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent?): Boolean {

                when (event?.action) {
                    MotionEvent.ACTION_MOVE -> {
                        if (binding.rightBar.x > event.rawX + view.width)
                            view.x = event.rawX
                    }
                }

                return true
            }
        })

        val rightBarInitialPos = binding.rightBar.y
        displayLog("Inital Righ Pos" + rightBarInitialPos)
        binding.rightBar.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                when (event?.action) {

                    MotionEvent.ACTION_MOVE -> {
                        displayLog("view x"+view.x)
                        displayLog("event x"+event.rawX)
                        if (binding.leftBar.x < event.rawX - view.width) {
                            view.x = event.rawX
                        }
                    }
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