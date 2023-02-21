package com.example.clima.ui.common

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
abstract class BaseViewModel : ViewModel(), KoinComponent {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showError = SingleLiveEvent<String>()
    val showError: LiveData<String> = _showError

    protected val _exception = MutableLiveData<Exception>()

    fun doAsyncWork(
        setLoader: Boolean = true,
        setError: Boolean = true,
        dispatcher: CoroutineDispatcher = IO,
        work: suspend () -> Unit = {}
    ) {
        viewModelScope.launch(dispatcher) {
            if (setLoader)
                setLoading(true)

            try {
                work()
            } catch (e: Exception) {
                if (setError)
                    setErrorMessage(e.message)

                setException(e)
//                Timber.e(e.message)
            }

            if (setLoader)
                setLoading(false)
        }
    }

    protected suspend fun setErrorMessage(message: String?) {
        withContext(Main) {
            _showError.call(message)
        }
    }

    @VisibleForTesting
    suspend fun setException(ex: Exception) {
        _exception.postValue(ex)
    }


    protected fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }
}