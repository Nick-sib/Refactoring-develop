package com.nick_sib.refactoringdevelop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.RecyclerviewItemSearchResultBinding
import com.nick_sib.refactoringdevelop.model.data.DataModel

class MainAdapter(
    val clickListener: ((DataModel) -> Unit)? = null
): RecyclerView.Adapter<BaseViewHolder>() {
    companion object {
        private const val SEARCH_RESUlT_IS_EMPTY = 0
        private const val SEARCH_RESUlT_SIMPLY = 1
    }

    var data: List<DataModel> = emptyList()
        set(value) {
            field = if (value.isEmpty())
                listOf(DataModel(null, null))
            else
                value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int =
        if ((data.size == 1) && (data[0].text == null) && (data[0].meanings.isNullOrEmpty()))
            SEARCH_RESUlT_IS_EMPTY
        else
            SEARCH_RESUlT_SIMPLY

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  if (viewType == SEARCH_RESUlT_IS_EMPTY) {
            RecyclerEmptySearchHolder(inflater
                .inflate(R.layout.recyclerview_item_empty_search_result, parent, false))
        } else {
            RecyclerItemViewHolder(RecyclerviewItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context)))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class RecyclerItemViewHolder(
        private var binding: RecyclerviewItemSearchResultBinding
    ): BaseViewHolder(binding.root) {
        override fun bind(data: DataModel) {
            binding.property = data
            binding.executePendingBindings()
            binding.root.setOnClickListener { clickListener?.invoke(data) }
//                itemView.setOnClickListener {
//                    clickListener?.invoke(data.text ?: "err")
//                }
        }
    }

    inner class RecyclerEmptySearchHolder(view: View): BaseViewHolder(view) {
        override fun bind(data: DataModel) {}
    }

}