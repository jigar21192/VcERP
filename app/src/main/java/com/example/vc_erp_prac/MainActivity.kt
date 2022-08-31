package com.example.vc_erp_prac


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vc_erp_prac.adapter.DataAdapter
import com.example.vc_erp_prac.databinding.ActivityMainBinding
import com.example.vc_erp_prac.model.CityModel
import com.example.vc_erp_prac.model.ContactModel
import com.example.vc_erp_prac.model.DistrictModel
import com.example.vc_erp_prac.model.StateModel
import com.example.vc_erp_prac.retrofit.Resource
import com.example.vc_erp_prac.viewModel.DataViewModel
import com.example.vc_erp_prac.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DataViewModel by viewModels {
        ViewModelFactory(application)
    }
    private var contactList = ArrayList<ContactModel>()
     private val createContactLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val model=data?.getSerializableExtra("data") as ContactModel
                contactList.add(model)
                binding.rvContact.adapter?.notifyDataSetChanged()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setObserver()
        setRecyclerView()
       // viewModel.getCityCall()
        //viewModel.getDistCall()
        //viewModel.getStateCall()
        viewModel.saveToDB()
        viewModel.getContactCall()

       clickListener()
    }

    private fun clickListener() {
        binding.apply {
            ivAdd.setOnClickListener(View.OnClickListener {
                createContactLauncher.launch(Intent(this@MainActivity, AddContactActivity::class.java))
            })
        }
    }

    private fun setRecyclerView() {
        binding.rvContact.layoutManager = LinearLayoutManager(this)
        binding.rvContact.adapter = DataAdapter(this, contactList,object :DataAdapter.OnClickListener{
            override fun onDeleteClick(position: Int) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle(getString(R.string.alert))
                builder.setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_contact))

                builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
                    viewModel.getDeleteContactById(contactList[position].Id)
                    contactList.removeAt(position)
                    binding.rvContact.adapter?.notifyDataSetChanged()
                }
                builder.setNegativeButton(getString(R.string.no)) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()

            }
        })

    }

    private fun setObserver() {

        //For API Use
        /*viewModel.getCity().observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pvProgress.visibility= View.GONE
                    it.data?.let {
                        cityList.addAll(it.cityArray)
                    }
                }
                Resource.Status.LOADING -> {
                        binding.pvProgress.visibility= View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    binding.pvProgress.visibility= View.GONE
                    it.let {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle(getString(R.string.alert))
                        builder.setMessage(getString(R.string.some_wrong))

                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                            dialogInterface.dismiss()
                        }
                        builder.setNegativeButton("") { dialogInterface, which ->

                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(true)
                        alertDialog.show()

                    }
                }
            }
        }

        viewModel.getDist().observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pvProgress.visibility= View.GONE
                    it.data?.let {
                        distList.addAll(it.districtArray)
                    }
                }
                Resource.Status.LOADING -> {
                    binding.pvProgress.visibility= View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    binding.pvProgress.visibility= View.GONE
                    it.let {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle(getString(R.string.alert))
                        builder.setMessage(getString(R.string.some_wrong))

                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                            dialogInterface.dismiss()
                        }
                        builder.setNegativeButton("") { dialogInterface, which ->

                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(true)
                        alertDialog.show()

                    }
                }
            }
        }

        viewModel.getState().observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pvProgress.visibility= View.GONE
                    it.data?.let {
                        stateList.addAll(it.statesArray)
                    }
                }
                Resource.Status.LOADING -> {
                    binding.pvProgress.visibility= View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    binding.pvProgress.visibility= View.GONE
                    it.let {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle(getString(R.string.alert))
                        builder.setMessage(getString(R.string.some_wrong))

                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                            dialogInterface.dismiss()
                        }
                        builder.setNegativeButton("") { dialogInterface, which ->

                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(true)
                        alertDialog.show()

                    }
                }
            }
        }*/

        viewModel.getContacts().observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    contactList.addAll(it.data?.contactArray!!)
                    binding.rvContact.adapter?.notifyDataSetChanged()
                }
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {

                }
            }
        }
    }
}