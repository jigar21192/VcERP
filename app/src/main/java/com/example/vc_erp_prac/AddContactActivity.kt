package com.example.vc_erp_prac

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.vc_erp_prac.adapter.ViewPagerSwipeAdapter
import com.example.vc_erp_prac.databinding.ActivityAddContactBinding
import com.example.vc_erp_prac.fragment.AddressFragment
import com.example.vc_erp_prac.fragment.GeneralFragment
import com.example.vc_erp_prac.model.ContactModel
import com.example.vc_erp_prac.viewModel.DataViewModel
import com.example.vc_erp_prac.viewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AddContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    private lateinit var adapter: ViewPagerSwipeAdapter
    private  var contactModel:ContactModel?=null
    private val viewModel: DataViewModel by viewModels {
        ViewModelFactory(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact)

        setUpView()
        clickListener()
    }

    private fun clickListener() {
        binding.apply {
            tvSubmit.setOnClickListener(View.OnClickListener {
                if((adapter.getFragment(0) as GeneralFragment).validation()){
                  contactModel=  (adapter.getFragment(0) as GeneralFragment).getData()
                    vpContact.currentItem=1
                    if((adapter.getFragment(1) as AddressFragment).validation()) {
                        val model = (adapter.getFragment(1) as AddressFragment).getData()
                        contactModel?.address = model.address
                        contactModel?.cityName = model.cityName
                        contactModel?.distName = model.distName
                        contactModel?.stateName = model.stateName

                        viewModel.storeContactData(contactModel)

                        val returnIntent = Intent()
                        returnIntent.putExtra("data",contactModel)
                        setResult(RESULT_OK, returnIntent)
                        finish()
                    }
                }
            })
            tvCancel.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            ivBack.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
        }
    }

    private fun setUpView() {
        setupViewPager()
        setupTabIcons()
    }


    private fun setupViewPager() {

        adapter = ViewPagerSwipeAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(GeneralFragment(), resources.getString(R.string.general))
        adapter.addFragment(AddressFragment(), resources.getString(R.string.address))
        binding.vpContact.offscreenPageLimit=2
        binding.vpContact.adapter = adapter


        TabLayoutMediator(
            binding.tabs, binding.vpContact
        ) { _, _ -> // Styling each tab here
        }.attach()
    }

    private fun setupTabIcons() {
        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = resources.getString(R.string.general)
        tabOne.isAllCaps = false
        tabOne.setTextColor(ContextCompat.getColor(this.applicationContext!!, R.color.c_0083ff))
        binding.tabs.getTabAt(0)!!.customView = tabOne
        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = resources.getString(R.string.address)
        tabTwo.isAllCaps = false
        tabTwo.setTextColor(ContextCompat.getColor(this.applicationContext!!, R.color.c_adaeb3))
        binding.tabs.getTabAt(1)!!.customView = tabTwo
        binding.vpContact.currentItem = 0
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val textView = tab.customView as TextView?
                textView?.setTextColor(
                    ContextCompat.getColor(
                        this@AddContactActivity.applicationContext!!,
                        R.color.c_0083ff
                    )
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val textView = (tab.customView as TextView?)!!
                textView.setTextColor(
                    ContextCompat.getColor(
                        this@AddContactActivity.applicationContext!!,
                        R.color.c_adaeb3
                    )
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}