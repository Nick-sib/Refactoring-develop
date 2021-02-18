package com.nick_sib.refactoringdevelop.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nick_sib.refactoringdevelop.model.data.DataModel

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: DataModel)
}