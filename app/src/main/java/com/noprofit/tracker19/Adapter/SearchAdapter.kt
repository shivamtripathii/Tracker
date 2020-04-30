package com.noprofit.tracker19.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.noprofit.tracker19.Models.Countries.Countries
import com.noprofit.tracker19.R
import com.noprofit.tracker19.Utils.timestampIntoDate
import kotlinx.android.synthetic.main.item_search.view.*
import kotlinx.android.synthetic.main.item_search.view.recovered
import kotlinx.android.synthetic.main.item_search.view.test
import kotlinx.android.synthetic.main.item_search.view.time
import kotlinx.android.synthetic.main.item_search.view.todaycases
import kotlinx.android.synthetic.main.item_search.view.todaydeath
import kotlinx.android.synthetic.main.item_search.view.ttodaycases
import kotlinx.android.synthetic.main.item_search.view.ttodaydeath

class SearchAdapter(
    var context: Context,
    var list: ArrayList<Countries>, var onClick: onSearchClick

) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(), Filterable {

    var onClickList = onClick
    var con = context
    var listFull = ArrayList<Countries>(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view, onClickList)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    inner class SearchViewHolder(
        itemView: View,
        onClickList: onSearchClick
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var onSearchClickListener = onClickList

        fun bind(data: Countries) {
            itemView.name.text = data.country
            this.onSearchClickListener = onClickList
            itemView.setOnClickListener(this)
            itemView.time.text = timestampIntoDate(data.updated)
            itemView.todaycases.text = data.todayCases.toString()
            itemView.ttodaycases.text = data.cases.toString()
            itemView.todaydeath.text = data.todayDeaths.toString()
            itemView.ttodaydeath.text = data.deaths.toString()
            itemView.recovered.text = data.recovered.toString()
            itemView.test.text = data.tests.toString()
        }

        override fun onClick(v: View?) {
            onSearchClickListener.onclick(adapterPosition,itemView)
        }
    }

    interface onSearchClick {
        fun onclick(position: Int,itemView: View)
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterList = ArrayList<Countries>()
                if (constraint == null || constraint.isEmpty()) {
                    filterList.addAll(listFull)
                } else {
                    var pattern = constraint.toString().toLowerCase().trim()
                    for (data: Countries in listFull) {
                        if (data.country.toLowerCase().contains(pattern)) {
                            filterList.add(data)
                        }
                    }
                }
                val result = FilterResults()
                result.values = filterList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list.clear()
                list.addAll((results!!.values) as (Collection<Countries>))
                notifyDataSetChanged()
            }

        }

    }
}