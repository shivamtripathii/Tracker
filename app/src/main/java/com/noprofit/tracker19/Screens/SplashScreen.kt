package com.noprofit.tracker19.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.noprofit.tracker19.R
import com.noprofit.tracker19.Utils.Check
import com.noprofit.tracker19.Utils.snackbar

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        /*appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                Log.d("what","update")
            }else
            {
                Log.d("what","update1")
            }
        }

        appUpdateInfoTask.addOnCompleteListener {
            Log.d("what",it.result.toString())
        }*/
    }

    fun startTrackActivity(view: View) {
        Check.setInfo(0)
        startActivity(Intent(this, CovidTrackHome::class.java))
        //finish()
    }

    fun startTrackActivityWrold(view: View) {
        val intent = Intent(this,CovidWeb::class.java)
        intent.putExtra("key",2)
        startActivity(intent)
    }
    fun startTrackActivityIndia(view: View) {
        val intent = Intent(this,CovidWeb::class.java)
        intent.putExtra("key",3)
        startActivity(intent)
    }

    fun startTrackActivityMap(view: View) {
        val intent = Intent(this,CovidWeb::class.java)
        intent.putExtra("key",1)
        startActivity(intent)
    }
}
