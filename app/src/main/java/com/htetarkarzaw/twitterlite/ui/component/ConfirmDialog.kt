package com.htetarkarzaw.twitterlite.ui.component

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.htetarkarzaw.twitterlite.databinding.DialogConfirmBinding

class ConfirmDialog(
    context: Context
) : AlertDialog(context) {

    private var _binding: DialogConfirmBinding? = null
    val binding get() = _binding!!

    init {
        _binding = DialogConfirmBinding.inflate(LayoutInflater.from(context))
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
    }

    fun setUpDialog(title:String,message: String,onClickConfirm: () -> Unit) {
        binding.tvError.text = message
        binding.tvTitle.text = title
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnOkay.setOnClickListener {
            onClickConfirm()
            dismiss()
        }
        show()
    }
}