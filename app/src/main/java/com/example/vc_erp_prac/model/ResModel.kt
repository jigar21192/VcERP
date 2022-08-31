package com.example.vc_erp_prac.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResModel(
    var contactArray : ArrayList<ContactModel> = arrayListOf(),
    @SerializedName("cityArray" ) var cityArray : ArrayList<CityModel> = arrayListOf(),
    @SerializedName("statesArray" ) var statesArray : ArrayList<StateModel> = arrayListOf(),
    @SerializedName("districtArray" ) var districtArray : ArrayList<DistrictModel> = arrayListOf()
):Serializable
