package com.nick_sib.refactoringdevelop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.model.data.DataModel

class MainAdapter(
val clickListener: ((String) -> Unit)? = null
): RecyclerView.Adapter<BaseViewHolder>() {

    private val SEARCH_RESUT_IS_EMPTY = 0
    private val SEARCH_RESUT_SIMPLY = 1

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
            SEARCH_RESUT_IS_EMPTY
        else
            SEARCH_RESUT_SIMPLY

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  if (viewType == SEARCH_RESUT_IS_EMPTY) {
            RecyclerEmptySearchHolder(inflater
                .inflate(R.layout.recyclerview_item_empty_search_result, parent, false))
        } else {
            RecyclerItemViewHolder(inflater
                .inflate(R.layout.recyclerview_item_search_result, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int =
        data.size

    inner class RecyclerItemViewHolder(view: View): BaseViewHolder(view) {
        override fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.tv_header).text = data.text ?: ""
                itemView.findViewById<TextView>(R.id.tv_description).text =
                    data.meanings?.get(0)?.translation?.text ?: ""

                itemView.setOnClickListener {
                    clickListener?.invoke(data.text ?: "err")
                }
            }
        }
    }

    inner class RecyclerEmptySearchHolder(view: View): BaseViewHolder(view) {
        override fun bind(data: DataModel) {}
    }

}