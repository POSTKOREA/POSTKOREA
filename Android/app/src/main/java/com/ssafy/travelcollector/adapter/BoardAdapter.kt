package com.ssafy.travelcollector.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.databinding.BoardPostRvItemBinding
import com.ssafy.travelcollector.dto.Board
import com.ssafy.travelcollector.util.TimeConverter

class BoardAdapter : BaseAdapter<Board>() {

    inner class BoardHolder(private val binding: BoardPostRvItemBinding) : BaseHolder(binding){
        override fun bindInfo(data: Board) {
            binding.boardPostTitle.text = data.title
            binding.boardPostDate.text = TimeConverter.timeMilliToDateString(data.date)
            binding.root.setOnClickListener {
                clickListener.onClick(data.id)
            }
            binding.photoIcon.visibility = if(data.images.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return BoardHolder(BoardPostRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    interface ClickListener{
        fun onClick(id: Int)
    }

    lateinit var clickListener: ClickListener

}