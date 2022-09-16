package com.zpr.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zpr.weatherapp.R
import com.zpr.weatherapp.service.model.List
import com.zpr.weatherapp.utils.Utils
import kotlin.math.roundToInt

class ForecastListAdapter : RecyclerView.Adapter<ForecastListAdapter.ProductViewHolder>() {
    private var forecastList: ArrayList<List> = arrayListOf()

    fun setForecastList(forecastList: ArrayList<List>) {
        this.forecastList = forecastList.distinctBy { it.getDay() } as ArrayList<List>
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var temperature = itemView.findViewById<TextView>(R.id.temperature)
        var day = itemView.findViewById<TextView>(R.id.day)
        var weatherTypeImg = itemView.findViewById<ImageView
                >(R.id.weatherTypeImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_temeprature_upcoming_days, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.temperature.text = Utils.convertToTemp(forecastList[position].main?.temp)
        holder.day.text = forecastList[position].getDay()
        holder.weatherTypeImg.setImageResource(
            when (forecastList[position].weather[0].id) {
                in 500..531 -> R.mipmap.rain
                in 801..804 -> R.mipmap.partlysunny
                800 -> R.mipmap.clear
                else -> R.mipmap.partlysunny
            }
        )
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}