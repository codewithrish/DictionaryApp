package com.codewithrish.dictionaryapp.presentation.home

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithrish.dictionaryapp.R
import com.codewithrish.dictionaryapp.data.remote.dto.general.Definition
import com.codewithrish.dictionaryapp.databinding.ListItemDefinitionBinding

class DefinitionAdapter : ListAdapter<Definition, DefinitionAdapter.DefinitionViewHolder>(DefinitionDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val binding = ListItemDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefinitionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class DefinitionViewHolder(private val binding: ListItemDefinitionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Definition) {
            binding.tvDefinition.setTypeface(null, Typeface.BOLD_ITALIC)
            binding.tvDefinition.text = itemView.context.resources.getString(R.string.definition, adapterPosition + 1, item.definition)
            if (item.synonyms.isNotEmpty()) {
                binding.tvSynonyms.visibility = View.VISIBLE
                binding.tvSynonyms.text = itemView.context.resources.getString(R.string.synonyms, item.synonyms.joinToString(", "))
            }
            if (item.antonyms.isNotEmpty()) {
                binding.tvAntonyms.visibility = View.VISIBLE
                binding.tvAntonyms.text = itemView.context.resources.getString(R.string.antonyms, item.antonyms.joinToString(", "))
            }
            if (!item.example.isNullOrEmpty()) {
                binding.tvExample.visibility = View.VISIBLE
                binding.tvExample.text = item.example
                binding.tvExample.text = itemView.context.resources.getString(R.string.example, item.example)
            }
        }
    }

    class DefinitionDiffUtil :DiffUtil.ItemCallback<Definition>() {
        override fun areItemsTheSame(oldItem: Definition, newItem: Definition): Boolean {
            return oldItem.definition == newItem.definition
        }

        override fun areContentsTheSame(oldItem: Definition, newItem: Definition): Boolean {
            return oldItem == newItem
        }

    }


}