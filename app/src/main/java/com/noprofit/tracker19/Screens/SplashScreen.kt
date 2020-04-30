package com.noprofit.tracker19.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.noprofit.tracker19.R
import com.noprofit.tracker19.Utils.Check

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startTrackActivity(view: View) {
        Check.setInfo(0)
        startActivity(Intent(this, CovidTrackHome::class.java))
        //finish()
    }
}
