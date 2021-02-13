package com.nick_sib.refactoringdevelop.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.model.data.DataModel

class MainAdapter(
    val clickListener: ((String) -> Unit)? = null
): RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    var data: List<DataModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_search_result, parent, false))


    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int =
            data.size


    inner class RecyclerItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(data: DataModel) {
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

}