package com.example.meetingdata

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingdata.databinding.ActivityMainBinding
import com.example.meetingdata.models.User
import java.time.Instant
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity() {
    private val meetingMap = mutableMapOf<String, MutableList<User>>()
    private lateinit var binding: ActivityMainBinding

    var max = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readFromAsset()

        showData()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showData() {
        binding.apply {
            maxUsersValue.text = findMax()
            minUsersValue.text = findMin()
            uniqueUsersValue.text = "Unique Users: ${getUniqueNumberOfUsers()}"
        }
    }

    private fun readFromAsset() {
        val fileName = "meetingdata.txt"
        val bufferReader = application.assets.open(fileName).bufferedReader()
        bufferReader.useLines { str ->
            var meetingId = ""
            str.forEach {
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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findMax(): String {
        var time = ""
        meetingMap.forEach { v, u ->
            if (u.size > max) {
                max = u.size
                time = v
            }
        }
        return "Time: ${getDateTime(time)} - $max"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findMin(): String {
        var min = max
        var time = ""
        meetingMap.forEach { v, u ->
            if (u.size < min && u.size > 0) {
                min = u.size
                time = v
            }
        }
        return "Time: ${getDateTime(time)} - $min"
    }

    fun getUniqueNumberOfUsers(): Int {
        val userSet = mutableSetOf<User>()
        meetingMap.forEach { v, u ->
            userSet.addAll(u)
        }
        return userSet.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateTime(time: String): String {
        val dt = Instant.ofEpochSecond(time.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        val time = dt.toString().split("T")
        return time.last()
    }
}
