package com.cinemavista.client.view.advanced_ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.core.content.ContextCompat
import com.cinemavista.client.R
import com.cinemavista.client.databinding.PopupLayoutBinding

interface PopUpDialogListener{
    fun onClickListener()
}

fun Activity.showPopUpDialog(
    textDesc: String,
    backgroundImage: Int,
    listener: PopUpDialogListener?= null
){
    val dialog = Dialog(this)
    val binding = PopupLayoutBinding.bind(layoutInflater.inflate(R.layout.popup_layout, null))
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)
    dialog.setCancelable(listener == null)
    binding.apply {
        tvPopUp.text = textDesc
        ivPopup.background = ContextCompat.getDrawable(this@showPopUpDialog, backgroundImage)
        btnPopup.setOnClickListener {
            listener?.onClickListener()
            dialog.dismiss()
        }
        if(!isFinishing) dialog.show()
    }
}