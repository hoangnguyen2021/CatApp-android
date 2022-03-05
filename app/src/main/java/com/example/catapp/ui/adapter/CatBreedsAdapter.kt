package com.example.catapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catapp.data.model.catbreeds.CatBreed
import com.example.catapp.databinding.CatBreedsListItemBinding

class CatBreedsAdapter: RecyclerView.Adapter<CatBreedsAdapter.CatBreedsViewHolder>() {
    inner class CatBreedsViewHolder(private val binding: CatBreedsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(catBreed: CatBreed) {
            binding.tvName.text = catBreed.name
            binding.tvDescription.text = catBreed.description
            binding.tvTemperament.text = catBreed.temperament

            if (catBreed.image != null) {
                Glide.with(binding.ivCatBreedImage.context).load(catBreed.image.url)
                    .into(binding.ivCatBreedImage)
            }

            // each news item click navigates to InfoFragment
            // passing selectedArticle as argument
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(catBreed.id)
                }
            }
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    private val callback = object : DiffUtil.ItemCallback<CatBreed>() {
        override fun areItemsTheSame(oldItem: CatBreed, newItem: CatBreed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatBreed, newItem: CatBreed): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedsViewHolder {
        // create a new view, which defines the UI of the list item
        val binding = CatBreedsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CatBreedsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatBreedsViewHolder, position: Int) {
        val catBreed = differ.currentList[position]
        // associate a ViewHolder with data
        holder.bind(catBreed)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}