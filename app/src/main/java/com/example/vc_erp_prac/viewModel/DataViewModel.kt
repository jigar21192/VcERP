package com.example.vc_erp_prac.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vc_erp_prac.model.*
import com.example.vc_erp_prac.retrofit.ApiServiceHelper
import com.example.vc_erp_prac.retrofit.Resource
import com.example.vc_erp_prac.room.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DataViewModel(context: Application) : BaseViewModel(context) {
    private var database: MyDatabase?
    private val getCityData = MutableLiveData<Resource<ResModel>>()
    private val getDistData = MutableLiveData<Resource<ResModel>>()
    private val getStateData = MutableLiveData<Resource<ResModel>>()
    private val getContactData = MutableLiveData<Resource<ResModel>>()
    init {
         database = MyDatabase.getInstance(context)
    }

    fun getCityCall() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cityData=database?.myDao()?.getCityData()
                if(cityData?.isEmpty()!!) {
                    getCityData.postValue(Resource.loading(null))
                    val usersFromApi = ApiServiceHelper.getCityData()
                    if (usersFromApi?.isSuccessful == true) {
                        getCityData.postValue(Resource.success(usersFromApi.body()))
                        storeCityData(usersFromApi.body()?.cityArray)
                    } else {
                        getCityData.postValue(
                            Resource.error(
                                super.errorHandler(
                                    usersFromApi?.code()!!,
                                    usersFromApi.errorBody()!!
                                ),
                                null
                            )
                        )

                    }
                }else{
                    val res=ResModel();
                    res.cityArray= (cityData as ArrayList<CityModel>?)!!
                    getCityData.postValue(Resource.success(res))
                }


            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }

    fun getDistCall() {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                val distData=database?.myDao()?.getDistData()
                if(distData?.isEmpty()!!) {
                    getDistData.postValue(Resource.loading(null))
                    val usersFromApi = ApiServiceHelper.getDistData()
                    if (usersFromApi?.isSuccessful == true) {
                        getDistData.postValue(Resource.success(usersFromApi.body()))
                        storeDistData(usersFromApi.body()?.districtArray)
                    } else {
                        getDistData.postValue(
                            Resource.error(
                                super.errorHandler(
                                    usersFromApi?.code()!!,
                                    usersFromApi.errorBody()!!
                                ),
                                null
                            )
                        )

                    }

                }else{
                    val res=ResModel();
                    res.districtArray= (distData as ArrayList<DistrictModel>?)!!
                    getCityData.postValue(Resource.success(res))
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }

    fun getStateCall() {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                val stateData=database?.myDao()?.getStateData()
                if(stateData?.isEmpty()!!) {
                    getStateData.postValue(Resource.loading(null))
                    val usersFromApi = ApiServiceHelper.getStateData()
                    if (usersFromApi?.isSuccessful == true) {
                        getStateData.postValue(Resource.success(usersFromApi.body()))
                        storeStateData(usersFromApi.body()?.statesArray)
                    } else {
                        getStateData.postValue(
                            Resource.error(
                                super.errorHandler(
                                    usersFromApi?.code()!!,
                                    usersFromApi.errorBody()!!
                                ),
                                null
                            )
                        )

                    }
                }
                else{
                    val res=ResModel();
                    res.statesArray= (stateData as ArrayList<StateModel>?)!!
                    getStateData.postValue(Resource.success(res))

                }

            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }


    fun getCity(): LiveData<Resource<ResModel>> = getCityData
    fun getDist(): LiveData<Resource<ResModel>> = getDistData
    fun getState(): LiveData<Resource<ResModel>> = getStateData
    fun getContacts(): LiveData<Resource<ResModel>> = getContactData


    fun storeCityData(data: ArrayList<CityModel>?) {
        viewModelScope.launch(Dispatchers.IO) {
            for (item in data!!){
                database?.myDao()?.insertCityData(item)
            }
        }
    }

    fun storeDistData(data: ArrayList<DistrictModel>?) {
        viewModelScope.launch(Dispatchers.IO) {
            for (item in data!!){
                database?.myDao()?.insertDistData(item)
            }
        }
    }

    fun storeStateData(data: ArrayList<StateModel>?) {
        viewModelScope.launch(Dispatchers.IO) {
            for (item in data!!){
                database?.myDao()?.insertStateData(item)
            }
        }
    }

    fun storeContactData(data: ContactModel?) {
        viewModelScope.launch(Dispatchers.IO) {
                database?.myDao()?.insertContactData(data!!)
        }
    }

    fun getDistByIdCall(id:Int) {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                val distData=database?.myDao()?.getDistListByStateID(id)
                val res=ResModel();
                res.districtArray= (distData as ArrayList<DistrictModel>?)!!
                getDistData.postValue(Resource.success(res))
            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }

    fun getCityByIdCall(id:Int) {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                val cityModel=database?.myDao()?.getCityListByDistID(id)
                val res=ResModel();
                res.cityArray= (cityModel as ArrayList<CityModel>?)!!
                getCityData.postValue(Resource.success(res))
            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }

    fun getDeleteContactById(id:Long) {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                database?.myDao()?.deleteContactByID(id)

            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }

    fun checkAlreadyExit(moNumber:String) :Int {
             try {
                return database?.myDao()?.checkAlreadyExit(moNumber)!!

            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        return 0
    }

    fun getContactCall() {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                val contactModel=database?.myDao()?.getContactData()
                val res=ResModel();
                res.contactArray= (contactModel as ArrayList<ContactModel>?)!!
                getContactData.postValue(Resource.success(res))
            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }

    fun saveToDB(){
        viewModelScope.launch(Dispatchers.IO) {

        val stateDBData=database?.myDao()?.getStateData()
        if(stateDBData?.isEmpty()!!) {
            database?.myDao()?.insertStateData(StateModel(1, 1, "Gujarat"))
            database?.myDao()?.insertStateData(StateModel(2, 2, "Rajasthan"))
        }
        val distDBData=database?.myDao()?.getDistData()
        if(distDBData?.isEmpty()!!) {
            database?.myDao()?.insertDistData(DistrictModel(1, 1,1, "Gandhinagar"))
            database?.myDao()?.insertDistData(DistrictModel(2, 1,2, "Mehasana"))
            database?.myDao()?.insertDistData(DistrictModel(3, 2,3, "Jodhpur"))
            database?.myDao()?.insertDistData(DistrictModel(4, 2,4, "Jaipur"))
        }
        val cityDBData=database?.myDao()?.getCityData()
        if(cityDBData?.isEmpty()!!) {
            database?.myDao()?.insertCityData(CityModel(1, 1,1, "Gandhinagar"))
            database?.myDao()?.insertCityData(CityModel(2, 1,2, "Adalaj"))
            database?.myDao()?.insertCityData(CityModel(3, 2,3, "Unja"))
            database?.myDao()?.insertCityData(CityModel(4, 2,4, "Sidhpur"))
            database?.myDao()?.insertCityData(CityModel(5, 3,5, "Pratapnagar"))
            database?.myDao()?.insertCityData(CityModel(6, 3,6, "Sardarpura"))
            database?.myDao()?.insertCityData(CityModel(7, 4,7, "Man sarovar"))
            database?.myDao()?.insertCityData(CityModel(8, 4,8, "Ramgardh"))

        }

        }
    }
}
