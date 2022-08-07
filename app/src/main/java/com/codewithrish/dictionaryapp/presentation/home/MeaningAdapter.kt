package com.codewithrish.dictionaryapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithrish.dictionaryapp.data.remote.dto.general.Meaning
import com.codewithrish.dictionaryapp.databinding.ListItemMeaningBinding


class MeaningAdapter : ListAdapter<Meaning, MeaningAdapter.MeaningViewHolder>(MeaningDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = ListItemMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MeaningViewHolder(private val binding: ListItemMeaningBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Meaning) {
            binding.tvPartOfSpeech.text = item.partOfSpeech.uppercase()
            val definitionAdapter = DefinitionAdapter()
            val dividerItemDecoration = DividerItemDecoration(
                binding.rvDefinitions.context,
                DividerItemDecoration.VERTICAL
            )
            binding.rvDefinitions.addItemDecoration(dividerItemDecoration)
            binding.rvDefinitions.adapter = definitionAdapter
            definitionAdapter.submitList(item.definitions)
        }
    }

    class MeaningDiffUtil :DiffUtil.ItemCallback<Meaning>() {
        override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem.partOfSpeech == newItem.partOfSpeech
        }

        override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem == newItem
        }

    }


}