package com.example.vc_erp_prac.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "StateModel")
data class StateModel(
    @PrimaryKey(autoGenerate = true) var Id: Int,
    @SerializedName("stateId"   ) var stateId   : Int?    = null,
    @SerializedName("stateName" ) var stateName : String? = null
):Serializable
