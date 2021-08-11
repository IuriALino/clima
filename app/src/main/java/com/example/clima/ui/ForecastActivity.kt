package com.example.clima.ui

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import com.example.clima.data.CityEnum
import com.example.clima.data.Model
import com.example.clima.databinding.ActivityForecastBinding
import com.example.clima.ui.common.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ForecastActivity : BaseActivity<ActivityForecastBinding>() {

    companion object {
        private const val ARG_MESSAGE_RES = "message_res"
        fun newIntent(context: Context, @StringRes message: Int? = null) =
            Intent(context, ForecastActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(ARG_MESSAGE_RES, message)
                }
    }

    private val weatherViewModel by viewModel<WeatherViewModel>()

    val cities = listOf(
        Model(CityEnum.MELBOURNE), Model(CityEnum.MONTE_CARLO), Model(CityEnum.SAO_PAULO), Model(
            CityEnum.SILVERSTONE)
    )

    override fun initUIComponents() {

    }

    override fun initUIEvents() {
        cities.forEach {
            weatherViewModel.requestForecast(it.cityEnum)
        }
    }

    override fun subscribeUI() {

    }
    override fun getViewBinding() = ActivityForecastBinding.inflate(layoutInflater)
}