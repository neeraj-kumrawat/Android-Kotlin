package com.challenge.acronym.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.acronym.databinding.ItemLfsBinding
import com.challenge.acronym.data.Lfs

class AcronymAdapter :
    RecyclerView.Adapter<AcronymAdapter.LfsViewHolder>() {

    inner class LfsViewHolder(
        private val binding: ItemLfsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lfs: Lfs) {
            binding.lfs = lfs
        }
    }

    private val lfsList = ArrayList<Lfs>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(lfsList: List<Lfs>) {
        this.lfsList.clear()
        this.lfsList.addAll(lfsList)
        notifyDataSetChanged()
    }

    private lateinit var binding: ItemLfsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LfsViewHolder {
        binding = ItemLfsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LfsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LfsViewHolder, position: Int) {
        val largeNews = lfsList[position]
        holder.bind(largeNews)
    }

    override fun getItemCount(): Int = lfsList.size
}