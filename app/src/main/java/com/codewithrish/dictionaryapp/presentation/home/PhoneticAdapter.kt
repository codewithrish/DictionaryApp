package com.codewithrish.dictionaryapp.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithrish.dictionaryapp.R
import com.codewithrish.dictionaryapp.data.remote.dto.general.Phonetic

class PhoneticAdapter: ListAdapter<Phonetic, PhoneticAdapter.PhoneticViewHolder>(PhoneticDiffUtil()) {

    var speakPhonetic: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneticViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhoneticViewHolder(inflater.inflate(R.layout.list_item_phonetic, parent, false))
    }

    override fun onBindViewHolder(holder: PhoneticViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PhoneticViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvPhonetic = view.findViewById<TextView>(R.id.tv_phonetic)
        private val imgCountry = view.findViewById<ImageView>(R.id.img_country)
        private val imgSpeak = view.findViewById<ImageView>(R.id.img_speak)

        private val resources = itemView.context.resources

        fun bind(item: Phonetic) {

            tvPhonetic.text = item.text
            if (item.audio?.endsWith("uk.mp3") == true) {
                imgCountry.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.img_uk, null))
            } else if (item.audio?.endsWith("us.mp3") == true) {
                imgCountry.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.img_usa, null))
            } else {
                imgCountry.visibility = View.GONE
                imgSpeak.visibility = View.GONE
            }
            imgSpeak.setOnClickListener {
                item.audio?.let { it1 -> speakPhonetic?.invoke(it1) }
            }
        }
    }

    class PhoneticDiffUtil: DiffUtil.ItemCallback<Phonetic>() {
        override fun areItemsTheSame(oldItem: Phonetic, newItem: Phonetic): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: Phonetic, newItem: Phonetic): Boolean {
            return oldItem == newItem
        }

    }
}