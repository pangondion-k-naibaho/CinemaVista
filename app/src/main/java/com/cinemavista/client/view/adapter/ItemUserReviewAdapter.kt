package com.cinemavista.client.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cinemavista.client.R
import com.cinemavista.client.databinding.ItemUserReviewLayoutBinding
import com.cinemavista.client.model.Constants.URL.Companion.TMDBAVATAR_URL
import com.cinemavista.client.model.data_class.response.MovieReview
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ItemUserReviewAdapter(
    var data: MutableList<MovieReview>
): RecyclerView.Adapter<ItemUserReviewAdapter.ItemHolder>() {

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: MovieReview) = with(itemView){
            val binding = ItemUserReviewLayoutBinding.bind(itemView)
            binding.apply {

                if(item.author_details!!.avatar_path == null){
                    Glide.with(itemView.context)
                        .load(R.drawable.sample_avatar)
                        .into(ivUserAvatar)
                }else{
                    Glide.with(itemView.context)
                        .load(TMDBAVATAR_URL+item.author_details!!.avatar_path)
                        .into(ivUserAvatar)
                }
                tvUserName.text = item.author_details!!.username

                val ratingNDate = if(item.author_details!!.rating != null) "${item.author_details!!.rating}/10 - ${convertTimestampToDateString(item.updated_at!!)}" else convertTimestampToDateString(item.updated_at!!)

                tvuserRatingnDate.text = ratingNDate

                tvContentReview.text = item.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_review_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data.get(position))
    }

    private fun convertTimestampToDateString(timestamp: String): String {
        var response = ""
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val instant = Instant.parse(timestamp)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            response = dateTime.format(formatter)
        }

        return response
    }

    fun addItem(listReview: List<MovieReview>){
        val startPosition = data.size
        data.addAll(listReview)
        notifyItemRangeInserted(startPosition, listReview.size)
    }
}