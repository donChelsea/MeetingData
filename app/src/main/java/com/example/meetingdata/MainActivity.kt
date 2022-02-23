package com.example.meetingdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.meetingdata.models.Meeting
import com.example.meetingdata.models.User

class MainActivity : AppCompatActivity() {
    private val meetingMap = mutableMapOf<String, MutableList<User>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readFromAsset()
    }

    private fun readFromAsset() {
        val fileName = "meetingdata.txt"
        val bufferReader = application.assets.open(fileName).bufferedReader()
        bufferReader.useLines { str ->
            str.forEach {
                var meetingId = ""
                val split = it.split(" ")
                if (split.size == 1) {
                    meetingId = split[0]
                    if (!meetingMap.containsKey(meetingId)) {
                        meetingMap[meetingId] = mutableListOf()
                    }
                } else {
                    val user = User(split[0], "${split[1]} ${split[2]}")
                    meetingMap[meetingId]?.add(user)
                }
            }
        }
        Log.d("readFromAsset", "values" + meetingMap.values.toString())
        Log.d("readFromAsset", "keys" + meetingMap.keys.toString())

    }
}
