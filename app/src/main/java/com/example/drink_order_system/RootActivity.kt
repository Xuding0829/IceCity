package com.example.drink_order_system

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.model.getSky
import com.example.sunnyweather.ui.weather.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class RootActivity : AppCompatActivity() {
    private var rg_tab_bar: RadioGroup? = null
    private var rb_home: RadioButton? = null
    private var fg_home: Fragment? = null //点单界面
    private var fg_order: Fragment? = null //点单界面
    private var fg_shop: Fragment? = null //购物车界面
    private var fg_bill: Fragment? = null //查看历史订单界面
    private var fg_mine: Fragment? = null //点单界面
    private var fManager: FragmentManager? = null
    private var userName: String? = null

    val viewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)


        //if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        //}
        //if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        //}
        //if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        //}
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                //result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)

        userName = intent.getStringExtra("userName")
        println("username in oncreate in rootActivity $userName")
        // 获取FragmentManager的正确方式，使用getSupportFragmentManager
        fManager = supportFragmentManager
        rg_tab_bar = findViewById<View>(R.id.rg_tab) as RadioGroup
        rg_tab_bar!!.setOnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            this.onCheckedChanged(
                group,
                checkedId
            )
        } //对下方导航栏设置监听，实现界面切换
        //获取第一个单选按钮，并设置其为选中状态
        rb_home = findViewById<View>(R.id.rb_home) as RadioButton
        rb_home!!.isChecked = true
        //        rb_home = (RadioButton) findViewById(R.id.rb_order);
//        rb_home.setChecked(true);
        defaultFragment() //将点单界面设为默认界面
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

    fun defaultFragment() {
        val fTransaction = fManager!!.beginTransaction()
        fg_home = Fragment()
        fTransaction.add(R.id.ly_content, fg_home!!)
        fTransaction.show(fg_home!!)
        fTransaction.commit()

        //        FragmentTransaction fTransaction = fManager.beginTransaction();
//        fg_order = new Fragment();
//        fTransaction.add(R.id.ly_content, fg_order);
//        fTransaction.show(fg_order);
//        fTransaction.commit();
    }

    fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val fTransaction = fManager!!.beginTransaction()
        hideAllFragment(fTransaction)
        when (checkedId) {
            R.id.rb_home -> {
                if (!isFragmentCreated(fg_home)) {
//                    fg_home = new Fragment();
                    fg_home = HomeFragment.newInstance(userName)
                    (fg_home as HomeFragment?)?.let { fTransaction.add(R.id.ly_content, it) }
                }
                fTransaction.show(fg_home!!)
            }

            R.id.rb_order -> {
                if (!isFragmentCreated(fg_order)) {
                    fg_order = OrderFragment.newInstance(userName)
                    (fg_order as OrderFragment?)?.let { fTransaction.add(R.id.ly_content, it) }
                }
                fTransaction.show(fg_order!!)
            }

            R.id.rb_bill -> {
                if (!isFragmentCreated(fg_bill)) {
                    fg_bill = BillFragment.newInstance(userName)
                    (fg_bill as BillFragment?)?.let { fTransaction.add(R.id.ly_content, it) }
                }
                fTransaction.show(fg_bill!!)
            }

            R.id.rb_shop -> {
                if (!isFragmentCreated(fg_shop)) {
                    fg_shop = ShopFragment.newInstance(userName)
                    (fg_shop as ShopFragment?)?.let { fTransaction.add(R.id.ly_content, it) }
                }
                fTransaction.show(fg_shop!!)
            }

            R.id.rb_mine -> {
                if (!isFragmentCreated(fg_mine)) {
                    fg_mine = MineFragment.newInstance(userName)
                    (fg_mine as MineFragment?)?.let { fTransaction.add(R.id.ly_content, it) }
                }
                fTransaction.show(fg_mine!!)
            }
        }
        fTransaction.commit()
    }

    // 判断Fragment是否已经创建并添加到FragmentManager中
    private fun isFragmentCreated(fragment: Fragment?): Boolean {
        return fragment != null && fragment.isAdded
    }

    //隐藏所有Fragment
    private fun hideAllFragment(fragmentTransaction: FragmentTransaction) {
        if (fg_home != null) fragmentTransaction.hide(fg_home!!)
        if (fg_order != null) fragmentTransaction.hide(fg_order!!)
        if (fg_bill != null) fragmentTransaction.hide(fg_bill!!)
        if (fg_shop != null) fragmentTransaction.hide(fg_shop!!)
        if (fg_mine != null) fragmentTransaction.hide(fg_mine!!)
    }
}