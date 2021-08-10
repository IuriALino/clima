package com.example.clima.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected abstract fun initUIComponents()
    protected abstract fun initUIEvents()
    protected abstract fun subscribeUI()

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initUIComponents()
        initUIEvents()
        subscribeUI()
    }

    protected abstract fun getViewBinding(): VB
}