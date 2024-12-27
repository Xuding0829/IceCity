package com.example.drink_order_system

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.SunnyWeatherApplication.Companion.context
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.model.getSky
import com.example.sunnyweather.ui.place.PlaceViewModel
import com.example.sunnyweather.ui.weather.WeatherActivity
import com.example.sunnyweather.ui.weather.WeatherViewModel

class Place : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PlaceViewModel
    private lateinit var adapter: PlaceAdapter

    val viewModel1 by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

//        if( viewModel.isPlaceSaved()){
//            val place = viewModel.getSavedPlace()
//            val intent = Intent(context, RootActivity::class.java).apply {
//                putExtra("location_lng", place.location.lng)
//                putExtra("location_lat", place.location.lat)
//                putExtra("place_name", place.name)
//            }
//            startActivity(intent)
//            //activity?.finish()
//            return
//        }

        // 初始化RecyclerView相关设置
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        adapter = PlaceAdapter(viewModel.placeList)
        recyclerView.adapter = adapter

        viewModel1.weatherLiveData.observe(this, Observer { result ->
            val weather = result
            if(weather != null){
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                //result.exceptionOrNull()?.printStackTrace()
            }
        })

        // 获取搜索框控件并添加文本变化监听
        val searchPlaceEdit = findViewById<android.widget.EditText>(R.id.searchPlaceEdit)
        searchPlaceEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                val content = editable.toString()
                if (content.isNotEmpty()) {
                    viewModel.searchPlaces(content)
                } else {
                    // 隐藏RecyclerView，显示背景图片（简化处理，避免重复获取视图）
                    recyclerView.visibility = android.view.View.GONE
                    val bgImageView = findViewById<android.widget.ImageView>(R.id.bgImageView)
                    bgImageView.visibility = android.view.View.VISIBLE
                    // 清空列表数据
                    viewModel.placeList.clear()
                }
            }
        })

        // 观察数据变化并更新UI
        viewModel.placeLiveData.observe(this, Observer { result ->
            if (result!= null && result.isNotEmpty()) {
                // 显示RecyclerView，隐藏背景图片
                recyclerView.visibility = android.view.View.VISIBLE
                val bgImageView = findViewById<android.widget.ImageView>(R.id.bgImageView)
                bgImageView.visibility = android.view.View.GONE
                // 更新数据到Adapter，这里可以考虑优化为增量更新，如使用DiffUtil等方式
                adapter.updateData(result)
            } else {
                Toast.makeText(this, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showWeatherInfo(weather: Weather){
        val realtime = weather.realtime
        val daily = weather.daily
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        //val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        val currentSKY = getSky(realtime.skycon).info
        Address_message.SKY = currentSKY
        Address_message.temp = realtime.temperature.toInt()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // 这里可以进行回到上一界面后的相关操作，比如刷新界面等
            // 如果不需要额外操作，直接 finish 就可以回退到上一界面了
            finish()
        }
    }
}