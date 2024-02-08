package com.cinemavista.client.view.advanced_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cinemavista.client.R
import com.cinemavista.client.databinding.DiscoverChipgroupLayoutBinding
import com.cinemavista.client.model.Constants
import com.google.android.material.chip.Chip

class DiscoverChipGroupView: ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: DiscoverChipgroupLayoutBinding

    private var listener: DCGVListener?= null

    private var selectedGenres: MutableList<Int> = mutableListOf()

    constructor(context: Context):super(context){
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet){
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?){
        mContext = context

        binding = DiscoverChipgroupLayoutBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.discover_chipgroup_layout, this, true)
        )

        binding.btnVisibility.setOnClickListener {
            listener!!.onButtonVisibilityClicked()
        }

        binding.chipGroup.apply{
            //fill the code below the comment
            for(genre in Constants.GENRE_CONSTANTS::class.java.fields){
                if (genre.type == Int::class.java) {
                    val genreId = genre.getInt(null)
                    val genreName = genre.name.replace("_", " ").toLowerCase()

                    val chip = Chip(mContext)
                    chip.text = genreName
                    chip.isClickable = true
                    chip.isCheckable = true
                    chip.tag = genreId
                    chip.setOnCheckedChangeListener {_, isChecked ->
                        if(isChecked){
                            selectedGenres.add(genreId)
                        }else{
                            selectedGenres.remove(genreId)
                        }
                        listener?.onItemChipGroupClicked()
                    }

                    addView(chip)
                }

            }
        }

        binding.btnDiscoverBasedOnGenres.apply{
            //fill the code below the comment
            setOnClickListener{listener?.onButtonDiscoverBasedOnGenresClicked()}
        }

    }

    fun setListener(inputListener: DCGVListener){
        listener = inputListener
    }

    //make set data function (the input should be from interface, example the GENRE_CONSTANTS interface)

    fun getData(): List<Int>{
        return selectedGenres.toList()
    }

    fun isInvisible(): Boolean{
        return (binding.chipGroup.visibility == View.GONE && binding.btnDiscoverBasedOnGenres.visibility == View.GONE)
    }

    fun setInvisible(){
        binding.chipGroup.visibility = View.GONE
        binding.btnDiscoverBasedOnGenres.visibility = View.GONE
    }

    fun setVisible(){
        binding.chipGroup.visibility = View.VISIBLE
        binding.btnDiscoverBasedOnGenres.visibility = View.VISIBLE
    }

    interface DCGVListener{
        fun onButtonVisibilityClicked()

        fun onItemChipGroupClicked()

        fun onButtonDiscoverBasedOnGenresClicked()
    }
}