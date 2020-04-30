package com.noprofit.tracker19.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.noprofit.tracker19.Models.IndianStates.Regional
import com.noprofit.tracker19.R
import kotlinx.android.synthetic.main.item_states.view.*
import java.lang.Exception

class StateAdapter(var context: Context,var list: ArrayList<Regional>,var onStateListenter:OnStateListenerClick):RecyclerView.Adapter<StateAdapter.StateViewHolder>(),Filterable {

    val onState=onStateListenter
    var listFull = ArrayList<Regional>(list)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_states,parent,false)
        return StateViewHolder(view,onState)
    }
    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        var data = list[position]
        holder.bind(data)
    }

    inner class StateViewHolder(
        itemView: View,
        listener: OnStateListenerClick
    ):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        var lis :OnStateListenerClick = listener
        fun bind(data: Regional) {
            itemView.setOnClickListener(this)
            try {
                itemView.name.text = data.loc
                itemView.todaycasesstate.text = data.totalConfirmed.toString()
                itemView.todaydeath.text = data.deaths.toString()
                itemView.indiancases.text = data.confirmedCasesIndian.toString()
                itemView.outsidecases.text = data.confirmedCasesForeign.toString()
                itemView.discharged.text = data.discharged.toString()
            }catch (e:Exception)
            {
                Log.d("errormsg",e.message.toString())
            }
        }

        override fun onClick(v: View?) {
            lis.onStateClick(adapterPosition,itemView)
        }

    }

    interface OnStateListenerClick{
        fun onStateClick(position:Int,view: View)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterList = ArrayList<Regional>()
                if (constraint == null || constraint.isEmpty()) {
                    filterList.addAll(listFull)
                } else {
                    var pattern = constraint.toString().toLowerCase().trim()
                    for (data: Regional in listFull) {
                        if (data.loc.toLowerCase().contains(pattern)) {
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
                list.addAll((results!!.values) as (Collection<Regional>))
                notifyDataSetChanged()
            }

        }
    }
}