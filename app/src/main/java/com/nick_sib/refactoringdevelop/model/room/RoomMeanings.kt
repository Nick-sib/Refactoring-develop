package com.nick_sib.refactoringdevelop.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomMeanings(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val translation: String,
    val imageUrl: String?
)