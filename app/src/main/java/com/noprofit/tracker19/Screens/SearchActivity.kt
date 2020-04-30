package com.noprofit.tracker19.Screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.noprofit.tracker19.Adapter.CityAdapter
import com.noprofit.tracker19.Adapter.SearchAdapter
import com.noprofit.tracker19.Adapter.StateAdapter
import com.noprofit.tracker19.Models.Countries.Countries
import com.noprofit.tracker19.Models.IndianCity.DistrictData
import com.noprofit.tracker19.Models.IndianCity.IndianCity
import com.noprofit.tracker19.Models.IndianStates.IndianStates
import com.noprofit.tracker19.Models.IndianStates.Regional
import com.noprofit.tracker19.Network.Api
import com.noprofit.tracker19.Network.RetrofitInstance
import com.noprofit.tracker19.R
import com.noprofit.tracker19.Utils.*
import kotlinx.android.synthetic.main.activity_covid_track_home.loader
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_cites.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import kotlinx.android.synthetic.main.item_search.view.linearLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity(),
    SearchAdapter.onSearchClick, StateAdapter.OnStateListenerClick,CityAdapter.OnCityClick {
    lateinit var list:ArrayList<Countries>
    lateinit var listCity:ArrayList<DistrictData>
    lateinit var listState:ArrayList<Regional>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        selectList()
    }

    private fun selectList() {
        if(Check.getSearchInfo()==0)
            getList()
        else if(Check.getSearchInfo()==1)
            getStateList()
        else
            getCityList()
    }

    private fun getCityList() {
        loader.show()
        val retrofit = RetrofitInstance.getRetrofitInstance("https://api.covid19india.org/")
        val api = retrofit.create(Api::class.java)
        api.getIndianCites().enqueue(object : Callback<ArrayList<IndianCity>>{

            override fun onResponse(call: Call<ArrayList<IndianCity>>, response: Response<ArrayList<IndianCity>>) {
                if (response.isSuccessful && response.body()!=null) {
                    var data = ArrayList<DistrictData>()
                    for (dis in response.body()!!)
                    {
                        data.addAll(dis.districtData)
                    }
                    showCityList(data)
                }
                loader.smoothToHide()
            }

            override fun onFailure(call: Call<ArrayList<IndianCity>>, t: Throwable) {
                loader.smoothToHide()

            }

        })
    }

    private fun showCityList(data: ArrayList<DistrictData>) {
        listCity = data
        val adapter = CityAdapter(
            data as ArrayList<DistrictData>,
            this
        )
        searchlist.setHasFixedSize(true)
        searchlist.layoutManager = LinearLayoutManager(this)
        searchlist.adapter = adapter
        mainlayout.visibility = View.VISIBLE
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                adapter.filter.filter(s.toString())
            }
        })
    }

    private fun getList() {
        loader.show()
        //setTextLoading()
        val retrofit = RetrofitInstance.getRetrofitInstance(BASE_URL)
        val api = retrofit.create(Api::class.java)
        api.getCountries().enqueue(object : Callback<List<Countries>> {
            override fun onResponse(
                call: Call<List<Countries>>,
                response: Response<List<Countries>>
            ) {
                if (response.isSuccessful) {
                    showList(response.body())
                }
                loader.smoothToHide()
            }

            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                loader.smoothToHide()
                Log.d("errormsg", t.message.toString())
                snackbar(
                    this@SearchActivity,
                    "Failure",
                    -1
                )
            }
        })
    }

    fun getStateList()
    {
        loader.show()
        //setTextLoading()
        val retrofit = RetrofitInstance.getRetrofitInstance(BASE_URL_INDIA)
        val api = retrofit.create(Api::class.java)
        api.getIndianStates().enqueue(object : Callback<IndianStates> {
            override fun onResponse(call: Call<IndianStates>, response: Response<IndianStates>) {
                if (response.isSuccessful && response.body()!=null) {
                    listState = response.body()!!.data.regional as ArrayList<Regional>
                    showStateList(listState)
                }
                loader.smoothToHide()
            }

            override fun onFailure(call: Call<IndianStates>, t: Throwable) {
                loader.smoothToHide()
                Log.d("errormsg", t.message.toString())
                snackbar(
                    this@SearchActivity,
                    "Failure",
                    -1
                )
            }
        })
    }

    private fun showStateList(data: ArrayList<Regional>) {
        Log.d("fulllist",data.toString())
        val adapter = StateAdapter(
            this,
            data,
            this
        )
        searchlist.setHasFixedSize(true)
        searchlist.layoutManager = LinearLayoutManager(this)
        searchlist.adapter = adapter
        mainlayout.visibility = View.VISIBLE
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                adapter.filter.filter(s.toString())
            }
        })
    }

    private fun showList(data: List<Countries>?) {
        list = data as ArrayList<Countries>
        Log.d("fulllist",data.toString())
        val adapter = SearchAdapter(
            this,
            data as ArrayList<Countries>,
            this
        )
        searchlist.setHasFixedSize(true)
        searchlist.layoutManager = LinearLayoutManager(this)
        searchlist.adapter = adapter
        mainlayout.visibility = View.VISIBLE
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                adapter.filter.filter(s.toString())
            }
        })
    }

    override fun onclick(position: Int,itemView: View) {
        val data = list[position]
        if(itemView.linearLayout.isVisible) {
            itemView.linearLayout.visibility = View.GONE
            itemView.detais.text = "Show Details"
        }else
        {
            itemView.linearLayout.visibility = View.VISIBLE
            itemView.detais.text = "Hide Details"
        }
    }

    fun searchCountry(view: View) {

    }

    override fun onStateClick(position: Int, view: View) {
        if(view.linearLayout.isVisible) {
            view.linearLayout.visibility = View.GONE
            view.detais.text = "Show Details"
        }else
        {
            view.linearLayout.visibility = View.VISIBLE
            view.detais.text = "Hide Details"
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun goBack(view: View) {
        finish()
    }

    override fun onCityClick(position: Int, itemView: View) {
        val data = listCity[position]
        if(itemView.linearLayout.isVisible) {
            itemView.linearLayout.visibility = View.GONE
            itemView.detail.text = "Show Details"
        }else
        {
            itemView.linearLayout.visibility = View.VISIBLE
            itemView.detail.text = "Hide Details"
        }
    }

    /*fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }*/

}
