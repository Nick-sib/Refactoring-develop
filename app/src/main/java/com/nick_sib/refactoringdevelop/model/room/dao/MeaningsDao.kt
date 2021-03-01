package com.nick_sib.refactoringdevelop.model.room.dao

import androidx.room.*
import com.nick_sib.refactoringdevelop.model.data.Meanings
import com.nick_sib.refactoringdevelop.model.room.RoomMeanings

@Dao
interface MeaningsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RoomMeanings): Long

    @Update
    fun update(data: RoomMeanings)

    @Delete
    fun delete(data: RoomMeanings)

    @Query("SELECT * FROM RoomMeanings")
    fun getAll(): List<RoomMeanings>

    @Query("SELECT id from RoomMeanings WHERE dataId = :dataId AND translation = :text LIMIT 1")
    fun getIdByDataIdAndText(dataId: Long, text: String): Long?

    @Query("SELECT * from RoomMeanings WHERE dataId = :dataId")
    fun getItemByDataId(dataId: Long): List<RoomMeanings>

    fun insertOrUpdate(dataId: Long, data: List<Meanings>?):List<Long> = data?.map {
            getIdByDataIdAndText(dataId, it.text) ?:
            insert(RoomMeanings(dataId = dataId, translation = it.text, imageUrl = it.imageUrl))
        } ?: emptyList()
}