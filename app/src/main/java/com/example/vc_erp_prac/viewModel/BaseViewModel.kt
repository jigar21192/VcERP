package com.example.vc_erp_prac.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import okhttp3.ResponseBody

open class BaseViewModel(private val context: Application) :
    AndroidViewModel(context) {

    protected fun errorHandler(usersFromApi: Int, errorBody: ResponseBody): String {
        return when (usersFromApi) {
            500 -> {
                "Something went wrong"
            }
            401 -> {
                "Something went wrong"
            }
            else -> {
                "Something went wrong"
            }
        }
    }

}

