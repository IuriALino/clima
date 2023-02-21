package com.example.clima.ui.features.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clima.R
import com.example.clima.data.source.snackBarIndefinite
import com.example.clima.databinding.ActivityForecastBinding
import com.example.clima.ui.features.WeatherViewModel
import com.example.clima.ui.features.adapter.ForeCastAdapter
import com.example.clima.ui.features.common.BaseActivity
import com.example.clima.view.ProgressLoader
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.parameter.parametersOf

@KoinApiExtension
class ForecastActivity : BaseActivity<ActivityForecastBinding>() {
    private val _adapter by lazy { ForeCastAdapter() }
    private val weatherViewModel by viewModel<WeatherViewModel>()
    private val progressLoader by inject<ProgressLoader> { parametersOf(this) }

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
//            weatherViewModel.requestForecast(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeUI() {
        weatherViewModel.openForeCastModel.observe(this) {
            it?.let { forecast ->
                with(_adapter) {
                    this.forecast = forecast
                    notifyDataSetChanged()
                }
            }
        }
        weatherViewModel.openForeCastModel.observe(this) {
            it?.let { forecast ->
                with(_adapter) {
                    this.forecast = forecast
                    notifyDataSetChanged()
                }
            }
        }
        weatherViewModel.isLoading.observe(this) {
            progressLoader.setVisibility(it)
        }
        weatherViewModel.errorMessage.observe(this) { error ->
            intent.getStringExtra(ARG_MESSAGE_RES)?.let {
//                weatherViewModel.getForecast(it)
            }
            val msg = error ?: getString(R.string.error_invalid_response)
            binding.recyclerViewForecast.snackBarIndefinite(message = msg) { snackbar ->
                snackbar.dismiss()
            }
        }
    }

    override fun getViewBinding() = ActivityForecastBinding.inflate(layoutInflater)
}