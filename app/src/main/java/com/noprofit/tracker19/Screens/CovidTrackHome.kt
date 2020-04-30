package com.noprofit.tracker19.Screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.noprofit.tracker19.Models.GlobalModel
import com.noprofit.tracker19.Models.IndiaCountry.IndiaCountry
import com.noprofit.tracker19.Network.Api
import com.noprofit.tracker19.Network.RetrofitInstance
import com.noprofit.tracker19.R
import com.noprofit.tracker19.Utils.*
import com.noprofit.tracker19.Utils.Check.Companion.getInfo
import com.noprofit.tracker19.Utils.Check.Companion.setInfo
import com.noprofit.tracker19.Utils.Check.Companion.setSearchInfo
import com.github.pwittchen.swipe.library.rx2.Swipe
import com.github.pwittchen.swipe.library.rx2.SwipeListener
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_covid_track_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CovidTrackHome : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var retrofit: Retrofit
    lateinit var api: Api
    lateinit var activity: Activity
    lateinit var context: Context
    private var swipe: Swipe? = null
    private val disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_track_home)
        activity = this@CovidTrackHome
        context = this@CovidTrackHome
        loadScreen()
        swiperefresh.setOnRefreshListener(this)
        bottomBar.onItemSelected = {
            onNavigation(it)
        }
        swipe = Swipe()
        swipe!!.setListener(object : SwipeListener {
            override fun onSwipedUp(event: MotionEvent?): Boolean {
                return false
            }

            override fun onSwipedDown(event: MotionEvent?): Boolean {
                return false
            }

            override fun onSwipingUp(event: MotionEvent?) {

            }

            override fun onSwipedRight(event: MotionEvent?): Boolean {
                if(Check.getInfo()==1)
                {
                    bottomBar.setActiveItem(0)
                    onNavigation(0)
                }else if(Check.getInfo()==2)
                {
                    bottomBar.setActiveItem(1)
                    onNavigation(1)
                }
                return true
            }

            override fun onSwipingLeft(event: MotionEvent) {

            }

            override fun onSwipingRight(event: MotionEvent) {

            }

            override fun onSwipingDown(event: MotionEvent?) {

            }

            override fun onSwipedLeft(event: MotionEvent?): Boolean {
                if(getInfo()==0)
                {
                    bottomBar.setActiveItem(1)
                    onNavigation(1)
                }else if(getInfo()==1)
                {
                    bottomBar.setActiveItem(2)
                    onNavigation(2)
                }
                return true
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        swipe!!.dispatchTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    private fun loadScreen() {
        if(getInfo() == -1 || getInfo() == 0)
            getGlobalData()
        else if(Check.getInfo() == 1)
        {
            getIndiaData()
        }
        else
        {
            bottomBar.setActiveItem(0)
            getGlobalData()
        }
    }

    private fun getGlobalData() {
        setAnimation("wear_mask.json")
        loader.smoothToShow()
        setTextLoading()
        retrofit = RetrofitInstance.getRetrofitInstance(BASE_URL)
        api = retrofit.create(Api::class.java)
        api.getGlobal().enqueue(object : Callback<GlobalModel> {
            override fun onResponse(call: Call<GlobalModel>, response: Response<GlobalModel>) {
                if (response.isSuccessful) {
                    setValue(response.body())
                }
                loader.smoothToHide()
            }

            override fun onFailure(call: Call<GlobalModel>, t: Throwable) {
                loader.smoothToHide()
                snackbar(activity, "Failure", -1)
            }
        })
    }

    private fun onNavigation(pos: Int) {
        if (pos == 0) {
            getGlobalData()
            setInfo(0)
            setVisibility(pos)
        } else if (pos == 1) {
            getIndiaData()
            setInfo(1)
            setVisibility(pos)
        } else if (pos == 2) {
            setInfo(2)
            setVisibility(pos)
        }
    }

    private fun setVisibility(pos: Int) {

        if(pos == 1)
        {
            showSource.text = "Show Data Sources"
            about.visibility=View.GONE
            search.visibility = View.GONE
            searchIndia.visibility = View.VISIBLE
        }else if (pos ==0)
        {
            showSource.text = "Show Data Sources"
            about.visibility=View.GONE
            search.visibility = View.VISIBLE
            searchIndia.visibility = View.GONE
        }

        if (pos ==2)
        {
            profile.visibility = View.VISIBLE
            cardView12.visibility = View.GONE
            cardView.visibility = View.GONE
            linearLayout.visibility = View.GONE
            showSource.visibility = View.VISIBLE
        }else
        {
            profile.visibility = View.GONE
            cardView12.visibility = View.VISIBLE
            cardView.visibility = View.VISIBLE
            linearLayout.visibility = View.VISIBLE
            showSource.visibility = View.GONE
        }
    }

    private fun getIndiaData() {
        setAnimation("sneezing.json")
        loader.smoothToShow()
        setTextLoading()
        retrofit = RetrofitInstance.getRetrofitInstance(BASE_URL)
        api = retrofit.create(Api::class.java)
        api.getIndia().enqueue(object : Callback<IndiaCountry> {
            override fun onResponse(call: Call<IndiaCountry>, response: Response<IndiaCountry>) {
                if (response.isSuccessful) {
                    response.body()?.let { setValueCountry(it) }
                }
                loader.smoothToHide()
            }
            override fun onFailure(call: Call<IndiaCountry>, t: Throwable) {
                loader.smoothToHide()
                snackbar(activity, "Failure", -1)
            }
        })
    }

    private fun setAnimation(path: String) {
        animation_home.setAnimation(path)
        animation_home.loop(true)
        animation_home.playAnimation()
    }

    private fun setTextLoading() {
        val loadingMsg = getString(R.string.loading)
        time.text = loadingMsg
        todaycases.text = loadingMsg
        ttodaycases.text = loadingMsg
        todaydeath.text = loadingMsg
        ttodaydeath.text = loadingMsg
        recovered.text = loadingMsg
        test.text = loadingMsg
        flagText.text = loadingMsg
    }

    fun setValue(data: GlobalModel?) {
        time.text = timestampIntoDate(data!!.updated)
        todaycases.text = data.todayCases.toString()
        ttodaycases.text = data.cases.toString()
        todaydeath.text = data.todayDeaths.toString()
        ttodaydeath.text = data.deaths.toString()
        recovered.text = data.recovered.toString()
        test.text = data.tests.toString()
        flagText.text = "Global"
        flag.setImageDrawable(getDrawable(R.drawable.global))
        if (swiperefresh.isRefreshing)
            swiperefresh.isRefreshing = false
    }

    fun setValueCountry(data: IndiaCountry) {
        time.text = timestampIntoDate(data.updated)
        todaycases.text = data.todayCases.toString()
        ttodaycases.text = data.cases.toString()
        todaydeath.text = data.todayDeaths.toString()
        ttodaydeath.text = data.deaths.toString()
        recovered.text = data.recovered.toString()
        test.text = data.tests.toString()
        flagText.text = data.country.toString()
        Glide.with(this).load(data.countryInfo.flag).into(flag);
        if (swiperefresh.isRefreshing)
            swiperefresh.isRefreshing = false
    }

    override fun onRefresh() {
        loadScreen()
    }


    fun search(view: View) {
        setSearchInfo(0)
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun searchState(view: View) {
        setSearchInfo(1)
        startActivity(Intent(this, SearchActivity::class.java))
    }
    fun searchCity(view: View) {
        setSearchInfo(2)
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun linkn(view: View) {
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse("https://www.linkedin.com/in/shivamtripathii/")
        )
        startActivity(viewIntent)
    }

    fun showSource(view: View) {
        if (about.isVisible) {
            about.visibility = View.GONE
            showSource.text = "Show Data Sources"
        }
        else{
            about.visibility = View.VISIBLE
            showSource.text = "Hide Data Sources"
        }

    }

}
