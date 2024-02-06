package com.cinemavista.client.view.advanced_ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cinemavista.client.R
import com.cinemavista.client.databinding.InputsearchLayoutBinding


class InputSearchView: ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: InputsearchLayoutBinding

    private var listener: InputSearchListener?= null

    constructor(context: Context) : super(context){
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        init(context, attrs)
    }

    private fun init(context: Context, attributeSet: AttributeSet?){
        mContext = context

        binding = InputsearchLayoutBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.inputsearch_layout, this, true)
        )
        binding.etSearch.addTextChangedListener(textWatcher)
        binding.ivClear.setOnClickListener { listener?.onClearSearch() }
        binding.ivSearch.setOnClickListener { listener?.onClickSearch() }
    }

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if(binding.etSearch.text.toString().length > 0){
                binding.ivClear.visibility = View.VISIBLE
            }else binding.ivClear.visibility = View.GONE
        }

    }

    fun setTextHelper(text: String){
        binding.etSearch.hint = text
    }

    fun setIconClearInvisible(){
        binding.ivClear.visibility = View.GONE
    }

    fun clearText(){
        binding.etSearch.text.clear()
    }

    fun setListener(searchListener: InputSearchListener){
        listener = searchListener
    }

    fun getText(): String{
        return binding.etSearch.text.toString().toLowerCase()
    }

    interface InputSearchListener{
        fun onClickSearch()
        fun onClearSearch()
    }
}