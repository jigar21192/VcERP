package com.example.vc_erp_prac.room

import androidx.room.*
import com.example.vc_erp_prac.model.CityModel
import com.example.vc_erp_prac.model.ContactModel
import com.example.vc_erp_prac.model.DistrictModel
import com.example.vc_erp_prac.model.StateModel

@Dao
interface MyDao {

    @Insert()
    fun insertCityData(model: CityModel)

    @Insert()
    fun insertDistData(model: DistrictModel)

    @Insert()
    fun insertStateData(model: StateModel)

    @Insert()
    fun insertContactData(model: ContactModel)


    @Query("select * from `CityModel`")
    fun getCityData() : List<CityModel>

    @Query("select * from `DistrictModel`")
    fun getDistData() : List<DistrictModel>

    @Query("select * from `StateModel`")
    fun getStateData() : List<StateModel>

    @Query("select * from `ContactModel`")
    fun getContactData() : List<ContactModel>

    @Query("select * from `DistrictModel` where stateId=:id")
    fun getDistListByStateID(id:Int):List<DistrictModel>

    @Query("select * from `CityModel` where districtId=:id")
    fun getCityListByDistID(id:Int):List<CityModel>

    @Query("DELETE FROM `ContactModel` WHERE Id = :id")
    fun deleteContactByID(id:Long)

    @Query("SELECT COUNT() FROM `ContactModel` WHERE mobileNumber = :mobileNumber")
    fun checkAlreadyExit(mobileNumber: String): Int

}