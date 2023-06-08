package com.htetarkarzaw.twitterlite.ui.screen.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htetarkarzaw.twitterlite.R
import com.htetarkarzaw.twitterlite.databinding.ViewHolderFeedBinding
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.utils.DateTimeUtil.convertTimestampToRelativeTime

class FeedAdapter(
    private val onClickDetail: (Int) -> Unit
) :
    ListAdapter<FeedVO, RecyclerView.ViewHolder>(newDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ViewHolderFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FeedViewHolder).bind(getItem(position))
    }

    companion object {
        val newDiffUtil = object : DiffUtil.ItemCallback<FeedVO>() {
            override fun areItemsTheSame(oldItem: FeedVO, newItem: FeedVO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FeedVO, newItem: FeedVO): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun getClickItem(position: Int): FeedVO = getItem(position)

    inner class FeedViewHolder(private val binding: ViewHolderFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FeedVO?) {
            binding.tvName.text = item?.userName
            binding.tvTweet.text = item?.tweet ?: ""
            binding.tvTime.text = item?.let { convertTimestampToRelativeTime(it.date) }
            if (item?.photoUrl != null && item.photoUrl != "") {
                binding.ivTweetPhoto.visibility = View.VISIBLE
                Glide.with(itemView.context).load(item.photoUrl)
                    .placeholder(R.drawable.place_holder)
                    .into(binding.ivTweetPhoto)
            } else {
                binding.ivTweetPhoto.visibility = View.GONE
            }
            Glide.with(itemView.context).load(item?.userProfileUrl)
                .placeholder(R.drawable.img_profile_place_holder)
                .into(binding.ivProfile)
            itemView.setOnClickListener {
                onClickDetail(adapterPosition)
            }
        }
    }
}