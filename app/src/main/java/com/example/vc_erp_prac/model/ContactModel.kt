package com.example.vc_erp_prac.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "ContactModel")
data class ContactModel(
    @PrimaryKey var Id: Long,
    var firstName : String?    = null,
     var lastName     : String?    = null,
     var mobileNumber   : String? = null,
     var email   : String? = null,
     var address   : String? = null,
     var cityName   : String? = null,
     var distName   : String? = null,
     var stateName   : String? = null,
):Serializable
