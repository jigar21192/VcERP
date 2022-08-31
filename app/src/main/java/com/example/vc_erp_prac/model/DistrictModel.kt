package com.example.vc_erp_prac.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "DistrictModel")
data class DistrictModel(
    @PrimaryKey(autoGenerate = true) var Id: Int,
    @SerializedName("stateId"      ) var stateId      : Int?    = null,
    @SerializedName("districtId"   ) var districtId   : Int?    = null,
    @SerializedName("districtName" ) var districtName : String? = null
):Serializable
