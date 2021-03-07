package com.nick_sib.refactoringdevelop.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomDataModel(
    @PrimaryKey
    val id: Long,
    val favorite: Boolean = false,
    val text: String?,
)