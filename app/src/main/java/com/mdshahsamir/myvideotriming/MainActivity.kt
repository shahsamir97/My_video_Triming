package com.mdshahsamir.myvideotriming

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Motion
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
        displayLog("RightBar Init X : " + binding.leftBar.x)


        binding.leftBar.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> isDragging = true

                    MotionEvent.ACTION_MOVE -> if (isDragging) {
                        var newWidth = (event.x - view.x).toInt()
                        displayLog("Event X : " + event.x)
                        displayLog("View X : " + view.x)
                        displayLog("LeftBar New Width : " + newWidth)

                        if (newWidth < 100) {
                            newWidth = 100
                        }

                        val layoutParams = view.layoutParams
                        layoutParams.width = newWidth
                        view.layoutParams = layoutParams
                    }

                    MotionEvent.ACTION_CANCEL -> isDragging = false
                }

                return true
            }
        })

        val layoutWidth = binding.frameLayout.width
        binding.rightBar.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(view: View, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> isDragging = true

                    MotionEvent.ACTION_MOVE -> if (isDragging) {
//                        val newWidth = layoutWidth - event.x.toInt()
//                        displayLog("Event X : " + event.x)
//                        displayLog("Layout X : " + layoutWidth)
//                        displayLog("RightBar New Width : " + newWidth)
//
//                            val params = binding.rightBar.layoutParams
//                            params.width = newWidth
//                            view.layoutParams = params
                        var newWidth = (event.x - view.x).toInt()
                        displayLog("Event X : " + event.x)
                        displayLog("View X : " + view.x)
                        displayLog("LeftBar New Width : " + newWidth)

                        if (newWidth < 100) {
                            newWidth = 100
                        }

                        val layoutParams = view.layoutParams
                        layoutParams.width = newWidth
                        view.layoutParams = layoutParams
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