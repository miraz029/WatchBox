package com.miraz029.watchbox.api

import android.content.Context
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.miraz029.watchbox.data.model.Resource
import com.miraz029.watchbox.utilities.NetworkUtil

abstract class NetworkResource<RequestType>(context: Context) {

    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        if (NetworkUtil.isConnected(context)) {
            setValue(Resource.loading(null))
            fetchFromNetwork()
        } else {
            setValue(Resource.noInternet())
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = fetchFromApi()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)



            when (response) {
                is ApiSuccessResponse -> {
                    setValue(Resource.success(processResponse(response)))
                }

                is ApiErrorResponse -> {
                    setValue(Resource.error(response.errorMessage, null))
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<RequestType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract fun fetchFromApi(): LiveData<ApiResponse<RequestType>>
}