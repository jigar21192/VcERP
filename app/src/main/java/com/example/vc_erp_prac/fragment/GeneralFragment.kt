package com.example.vc_erp_prac.fragment

import android.app.Application
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.vc_erp_prac.R
import com.example.vc_erp_prac.databinding.FragmentGeneralBinding
import com.example.vc_erp_prac.model.ContactModel
import com.example.vc_erp_prac.viewModel.DataViewModel
import com.example.vc_erp_prac.viewModel.ViewModelFactory


class GeneralFragment : Fragment() {
    private lateinit var binding: FragmentGeneralBinding
    private val viewModel: DataViewModel by viewModels {
        ViewModelFactory(context?.applicationContext as Application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general, container, false)

        return binding.root
    }

    fun validation(): Boolean {
        var isError = true
        if (binding.etFirstName.text.toString().isEmpty()) {
            binding.etFirstName.error = getString(R.string.enter_first_name)
            binding.etFirstName.requestFocus()
            isError = false
        }else if (binding.etLastName.text.toString().isEmpty()) {
            binding.etLastName.error = getString(R.string.enter_last_name)
            binding.etLastName.requestFocus()
            isError = false
        }else if (binding.etMobileNo.text.toString().isEmpty()) {
            binding.etMobileNo.error = getString(R.string.please_enter_mobile)
            binding.etMobileNo.requestFocus()
            isError = false
        }else if(viewModel.checkAlreadyExit(binding.etMobileNo.text.toString())>0){
            binding.etMobileNo.error = getString(R.string.mobile_exits)
            binding.etMobileNo.requestFocus()
            isError = false
        }
        else if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = getString(R.string.please_enter_email)
            binding.etEmail.requestFocus()
            isError = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()
        ) {
            binding.etEmail.error = getString(R.string.please_enter_valid_email_address)
            binding.etEmail.requestFocus()
            isError = false
        }
        return isError
    }

    fun getData(): ContactModel {
        return ContactModel(
            System.currentTimeMillis(),
            binding.etFirstName.text.toString().trim(),
            binding.etLastName.text.toString().trim(),
            binding.etMobileNo.text.toString().trim(),
            binding.etEmail.text.toString().trim(),
            "",
            "",
            "",
            ""
        )
    }


}