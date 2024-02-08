package com.cinemavista.client.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cinemavista.client.R
import com.cinemavista.client.databinding.ItemMovieLayoutBinding
import com.cinemavista.client.model.Constants.URL.Companion.TMDBIMAGE_URL
import com.cinemavista.client.model.data_class.response.MovieInformation
import java.lang.Math.ceil

class ItemMovieAdapter(
    var data: MutableList<MovieInformation>,
    private val listener: ItemListener
): RecyclerView.Adapter<ItemMovieAdapter.ItemHolder>() {

    interface ItemListener{
        fun onItemClicked(item: MovieInformation)
    }

    inner class ItemHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(item: MovieInformation, listener:ItemListener) = with(itemView){
            val binding = ItemMovieLayoutBinding.bind(itemView)
            binding.apply {
                Glide.with(itemView.context)
                    .load(TMDBIMAGE_URL+item.poster_path)
                    .into(ivPosterMovie)

                tvMovieName.text = item.title
                tvMovieOriginalName.visibility = if(item.title.equals(item.original_title)) View.GONE else View.VISIBLE
                tvMovieOriginalName.text = "(${item.original_title})"

                val roundUpMovieRating = ceil(item.vote_average!! * 10) / 10
                tvMovieRating.text = "${roundUpMovieRating} / 10"

                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data.get(position), listener)
    }

    fun updateItem(newData: List<MovieInformation>?){
        try{
            if(newData != null){
                data.clear()
                data.addAll(newData)
                notifyDataSetChanged()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun addItem(listMovie: List<MovieInformation>){
        val startPosition = data.size
        data.addAll(listMovie)
        notifyItemRangeInserted(startPosition, listMovie.size)
    }
}