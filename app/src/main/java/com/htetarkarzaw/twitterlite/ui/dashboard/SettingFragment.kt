package com.htetarkarzaw.twitterlite.ui.dashboard

import android.widget.TextView
import androidx.fragment.app.viewModels
import com.htetarkarzaw.twitterlite.databinding.FragmentSettingBinding
import com.htetarkarzaw.twitterlite.ui.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    private val viewModel: SettingViewModel by viewModels()


    override fun observe() {
        val textView: TextView = binding.textDashboard
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

    override fun initUi() {

    }

}