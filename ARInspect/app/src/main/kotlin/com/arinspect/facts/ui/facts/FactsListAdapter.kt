package com.arinspect.facts.ui.facts

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.arinspect.facts.R
import com.arinspect.facts.databinding.ItemFactBinding
import com.arinspect.facts.model.RowsData
import com.squareup.picasso.Picasso

class FactsListAdapter : RecyclerView.Adapter<FactsListAdapter.ViewHolder>() {
    private lateinit var factList: List<RowsData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFactBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_fact, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(factList[position])
    }

    override fun getItemCount(): Int {
        return if (::factList.isInitialized) factList.size else 0
    }

    /**
     * update the fact list once fetched from the api call.
     */
    fun updateFactList(factList: List<RowsData>) {
        this.factList = factList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemFactBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = FactsViewModel()

        /**
         * binding every item of listview
         */
        fun bind(rowData: RowsData) {
            viewModel.bind(rowData)
            binding.viewModel = viewModel
            loadImage(binding.imageView, rowData.imageHref)
        }

        /**
         * to load images from url
         */
        fun loadImage(view: ImageView, imageUrl: String?) {
            Picasso.with(view.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(view)
        }
    }
}

