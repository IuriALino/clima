package com.example.clima.ui

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clima.databinding.ActivityForecastBinding
import com.example.clima.ui.adapter.ForeCastAdapter
import com.example.clima.ui.common.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ForecastActivity : BaseActivity<ActivityForecastBinding>() {
    private val _adapter by lazy { ForeCastAdapter() }
    private val weatherViewModel by viewModel<WeatherViewModel>()

    companion object {
        private const val ARG_MESSAGE_RES = "message_res"
        fun newIntent(context: Context, city: String) =
            Intent(context, ForecastActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(ARG_MESSAGE_RES, city)
                }
    }


    override fun initUIComponents() {
        binding.recyclerViewForecast.apply {
            layoutManager = LinearLayoutManager(this@ForecastActivity)
            adapter = _adapter
        }
    }


    override fun initUIEvents() {
        intent.getStringExtra(ARG_MESSAGE_RES)?.let {
            weatherViewModel.requestForecast(it)
        }
    }

    override fun subscribeUI() {
        weatherViewModel.openForeCastResponse.observe(this){
            it?.let { forecast ->
                with(_adapter) {
                    this.forecast = forecast.list
                    notifyDataSetChanged()
                }
            }
        }
    }
    override fun getViewBinding() = ActivityForecastBinding.inflate(layoutInflater)
}