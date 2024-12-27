package com.example.drink_order_system

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.ui.weather.WeatherActivity

class PlaceAdapter(private var placeList: MutableList<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    // 定义一个更新数据的方法，用于替换原有的notifyDataSetChanged全量更新方式（可优化为增量更新）
    fun updateData(newData: List<Place>) {
        placeList.clear()
        placeList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            val intent = Intent(parent.context, RootActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
                putExtra("check","1")
            }
            Address_message.place_name = place.name
            // 使用 startActivityForResult 启动，这里第二个参数是请求码，你可以自定义一个整数作为请求码，比如 1
            (parent.context as Activity).startActivityForResult(intent, 1)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeName: TextView = itemView.findViewById(R.id.placeName)
        val placeAddress: TextView = itemView.findViewById(R.id.placeAddress)
    }
}