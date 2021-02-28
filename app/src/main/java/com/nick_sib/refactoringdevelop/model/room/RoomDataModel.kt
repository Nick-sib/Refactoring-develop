package com.nick_sib.refactoringdevelop.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val favorite: Boolean = false,
    val text: String?,
)