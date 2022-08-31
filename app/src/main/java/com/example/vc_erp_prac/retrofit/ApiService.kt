package com.example.vc_erp_prac.retrofit

import com.example.vc_erp_prac.model.ResModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(getCityData)
    suspend fun getCityData(): Response<ResModel>

    @GET(getDistData)
    suspend fun getDistData(): Response<ResModel>

    @GET(getStateData)
    suspend fun getStateData(): Response<ResModel>

}