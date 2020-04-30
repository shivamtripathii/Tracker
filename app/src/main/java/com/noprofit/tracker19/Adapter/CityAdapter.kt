package com.noprofit.tracker19.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.noprofit.tracker19.Models.IndianCity.DistrictData
import com.noprofit.tracker19.R
import kotlinx.android.synthetic.main.item_cites.view.*

class CityAdapter(
    var list: ArrayList<DistrictData>,
    var onCityClick: OnCityClick) : RecyclerView.Adapter<CityAdapter.CityViewHolder>(),Filterable{

    var lis = onCityClick
    var listFull = ArrayList<DistrictData>(list)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cites,parent,false)
        return CityViewHolder(view,lis)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    inner class CityViewHolder(
        itemView: View,
        listen: OnCityClick
    ):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var listener :OnCityClick = listen
        override fun onClick(v: View?) {
            listener.onCityClick(adapterPosition,itemView)
        }

        fun bind(data: DistrictData) {
            itemView.setOnClickListener(this)
            itemView.name.text=data.district
            itemView.confirmed.text = data.confirmed.toString()
            itemView.death.text =data.deceased.toString()
            itemView.active.text=data.active.toString()
            itemView.recovered.text=data.recovered.toString()
        }
    }

    interface OnCityClick{
        fun onCityClick(position: Int,itemView: View)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterList = ArrayList<DistrictData>()
                if (constraint == null || constraint.isEmpty()) {
                    filterList.addAll(listFull)
                } else {
                    var pattern = constraint.toString().toLowerCase().trim()
                    for (data: DistrictData in listFull) {
                        if (data.district.toLowerCase().contains(pattern)) {
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
                list.addAll((results!!.values) as (Collection<DistrictData>))
                notifyDataSetChanged()
            }

        }
    }
}