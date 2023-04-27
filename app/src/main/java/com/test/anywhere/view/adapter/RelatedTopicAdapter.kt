package com.test.anywhere.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.test.anywhere.databinding.RelatedTopicItemBinding
import com.test.anywhere.model.RelatedTopic

class RelatedTopicAdapter(private var dataList: MutableList<RelatedTopic>, private val listener: ItemClickListener): RecyclerView.Adapter<RelatedTopicAdapter.ViewHolder>(), Filterable {

    var filteredList = dataList.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RelatedTopicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(filteredList[position]) {
                val number = position + 1
                binding.tvNo.text = "$number."
                binding.tvText.text = this.Text

                if (position % 2 == 0) {
                    itemView.setBackgroundColor(Color.LTGRAY)
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                }

                binding.itemView.setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString()
                filteredList = if (query.isEmpty()) {
                    dataList.toList()
                } else {
                    dataList.filter { it.Text.contains(query, true) }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<RelatedTopic>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(val binding: RelatedTopicItemBinding): RecyclerView.ViewHolder(binding.root)
}