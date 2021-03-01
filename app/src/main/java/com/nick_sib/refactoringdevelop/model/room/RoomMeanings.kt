package com.nick_sib.refactoringdevelop.model.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = RoomDataModel::class,
    parentColumns = ["id"],
    childColumns = ["dataId"],
    onDelete = ForeignKey.CASCADE)])
class RoomMeanings(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val dataId: Long? = null,
    val translation: String,
    val imageUrl: String?
)