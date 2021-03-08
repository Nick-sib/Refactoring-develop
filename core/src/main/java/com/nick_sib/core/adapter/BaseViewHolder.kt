package com.nick_sib.core.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nick_sib.model.DataModel

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: DataModel)
}