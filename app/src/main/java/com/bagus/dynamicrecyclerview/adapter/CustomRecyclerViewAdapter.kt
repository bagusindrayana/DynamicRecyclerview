package com.bagus.dynamicrecyclerview.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bagus.dynamicrecyclerview.utils.inflate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CustomRecyclerViewAdapter<T>(private val layout: Int, val onBind: (view: View, item: T, position: Int) -> Unit) : RecyclerView.Adapter<CustomRecyclerViewAdapter<T>.ViewHolder<T>>(),
    Filterable {
    var items = ArrayList<T>()
    var onItemClick:((pos:Int, view: View, item: T) -> Unit)? =null
    var itemsFilterList = ArrayList<T>()

    init {
        itemsFilterList = items
    }

    fun addItem(item: T) {
        this.items.add(item)
        this.itemsFilterList.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(items: ArrayList<T>) {
        this.items = items
        this.itemsFilterList = items
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        this.itemsFilterList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder<T>(private val view: View, val onBind: (view: View, item: T, position: Int) -> Unit) : RecyclerView.ViewHolder(view) ,
        View.OnClickListener{
        override fun onClick(p0: View?) {
            onItemClick?.invoke(adapterPosition,p0!!,itemsFilterList[adapterPosition])
        }

        fun bind(item: T, position: Int) {
            view.setOnClickListener(this)
            onBind(view, item, position)

        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder<T> {
        return ViewHolder(p0.inflate(layout), onBind)
    }

    override fun getItemCount(): Int = itemsFilterList.size

    override fun onBindViewHolder(holder: ViewHolder<T>, pos: Int) {
        try {
            holder.bind(itemsFilterList[pos], pos)
        } catch (e : Exception){
            Log.e("RECYCLERVIEW",e.message.toString()+e.toString())
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsFilterList = items
                } else {
                    val resultList = ArrayList<T>()
                    Log.i("ITEMS",items.toString())
                    for (row in items) {
                        if(row is String){
                            if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                                resultList.add(row)
                            }
                        } else {
                            if(row is Any){
                                for (prop in row.javaClass.kotlin.memberProperties) {

                                    val v = prop.get(row)
                                    Log.i("member",v.toString())
                                    if(v is String || v is Int){
                                        if (v.toString().toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(
                                                Locale.ROOT))) {
                                            resultList.add(row)
                                            break
                                        }
                                    }

                                }
                            } else {
                                Log.i("ROW",row.toString())
                            }
                        }



                    }
                    itemsFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsFilterList = results?.values as ArrayList<T>
                notifyDataSetChanged()
            }

        }
    }


}

