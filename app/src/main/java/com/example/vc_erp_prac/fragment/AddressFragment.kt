package com.example.vc_erp_prac.fragment

import android.app.Application
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.vc_erp_prac.R
import com.example.vc_erp_prac.adapter.CityAdapter
import com.example.vc_erp_prac.adapter.DistAdapter
import com.example.vc_erp_prac.adapter.StateAdapter
import com.example.vc_erp_prac.databinding.FragmentAddressBinding
import com.example.vc_erp_prac.model.CityModel
import com.example.vc_erp_prac.model.ContactModel
import com.example.vc_erp_prac.model.DistrictModel
import com.example.vc_erp_prac.model.StateModel
import com.example.vc_erp_prac.retrofit.Resource
import com.example.vc_erp_prac.room.MyDatabase
import com.example.vc_erp_prac.viewModel.DataViewModel
import com.example.vc_erp_prac.viewModel.ViewModelFactory


class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private var database: MyDatabase?=null
    var statelist:List<StateModel>? = null
    var distlist:List<DistrictModel>? = null
    var citylist:List<CityModel>? = null
    var selectedState:String=""
    var selectedCity:String=""
    var selectedDist:String=""
    private val viewModel: DataViewModel by viewModels {
        ViewModelFactory(context?.applicationContext as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        database = MyDatabase.getInstance(requireContext())
        viewModel.getStateCall()
        viewModel.getDist()
        setupSpinner()
        setObserver()
        return binding.root
    }

    private fun setObserver() {
        viewModel.getState().observe(this.viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    statelist=it.data?.statesArray
                    setupSpinner()
                }
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {

                }
            }
        }
        viewModel.getDist().observe(this.viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    distlist=it.data?.districtArray
                    updateDistData()
                }
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {

                }
            }
        }
        viewModel.getCity().observe(this.viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    citylist=it.data?.cityArray
                    updateCityData()
                }
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {

                }
            }
        }

    }

    private fun setupSpinner() {
            val customDropDownAdapter = statelist?.let { StateAdapter(requireContext(), it) }
            binding.spState.adapter = customDropDownAdapter
        binding.spState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.getDistByIdCall(statelist!!.get(p2).stateId!!)

                selectedState=statelist!!.get(p2).stateName.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    private fun updateDistData() {
            val customDropDownAdapter = distlist?.let { DistAdapter(requireContext(), it) }
            binding.spDist.adapter = customDropDownAdapter
        binding.spDist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.getCityByIdCall(distlist!![p2].districtId!!)
                selectedDist=distlist!!.get(p2).districtName.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        }

    private fun updateCityData() {
            val customDropDownAdapter = citylist?.let { CityAdapter(requireContext(), it) }
            binding.spCity.adapter = customDropDownAdapter
            binding.spCity.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedCity= citylist!![p2].cityName.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            })

    }

    public fun validation(): Boolean {
        var isError = true
        if (binding.etAddress.text.toString().isEmpty()) {
            binding.etAddress.error = getString(R.string.enter_address)
            binding.etAddress.requestFocus()
            isError = false
        }else if (selectedState.isEmpty()) {
            Toast.makeText(requireContext(),getString(R.string.select_state),Toast.LENGTH_SHORT).show()
            isError = false
        }
        else if (selectedDist.isEmpty()) {
            Toast.makeText(requireContext(),getString(R.string.select_dist),Toast.LENGTH_SHORT).show()
            isError = false
        }
        else if (selectedCity.isEmpty()) {
            Toast.makeText(requireContext(),getString(R.string.select_city),Toast.LENGTH_SHORT).show()
            isError = false
        }
        return isError
    }

    fun getData(): ContactModel {
        val model= ContactModel(1,""
            ,""
            ,""
            ,"",binding.etAddress.text.toString().trim()
            ,selectedCity,selectedDist,selectedState)
        return model
    }

}