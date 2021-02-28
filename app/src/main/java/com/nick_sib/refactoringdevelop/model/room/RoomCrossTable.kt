package com.nick_sib.refactoringdevelop.model.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomMeanings::class,
        parentColumns = ["id"],
        childColumns = ["meaningsId"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = RoomDataModel::class,
            parentColumns = ["id"],
            childColumns = ["datamodelId"],
        )]
)
data class RoomCrossTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val meaningsId: Long,
    val datamodelId: Long,
)


