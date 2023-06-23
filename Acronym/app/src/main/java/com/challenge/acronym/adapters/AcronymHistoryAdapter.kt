package com.challenge.acronym.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.acronym.data.AcronymsDetail
import com.challenge.acronym.databinding.ItemSfBinding

class AcronymHistoryAdapter :
    RecyclerView.Adapter<AcronymHistoryAdapter.SfViewHolder>() {

    inner class SfViewHolder(
        private val binding: ItemSfBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sf: AcronymsDetail) {
            binding.sf = sf
        }
    }

    private val sfList = ArrayList<AcronymsDetail>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(sfList: List<AcronymsDetail>) {
        this.sfList.clear()
        this.sfList.addAll(sfList)
        notifyDataSetChanged()
    }

    private lateinit var binding: ItemSfBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SfViewHolder {
        binding = ItemSfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SfViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SfViewHolder, position: Int) {
        val largeNews = sfList[position]
        holder.bind(largeNews)
    }

    override fun getItemCount(): Int = sfList.size
}