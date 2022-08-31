package com.example.vc_erp_prac.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "CityModel")
data class CityModel(
    @PrimaryKey(autoGenerate = true) var Id: Int,
    @SerializedName("districtId" ) var districtId : Int?    = null,
    @SerializedName("cityId"     ) var cityId     : Int?    = null,
    @SerializedName("cityName"   ) var cityName   : String? = null
):Serializable
