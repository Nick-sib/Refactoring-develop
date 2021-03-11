package com.nick_sib.refactoringdevelop.model.room.dao

import androidx.room.*
import com.nick_sib.refactoringdevelop.model.room.RoomDataModel

@Dao
interface DataModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RoomDataModel): Long

    @Update
    fun update(data: RoomDataModel)

    @Delete
    fun delete(data: RoomDataModel)

    @Query("SELECT id from RoomDataModel WHERE text= :text LIMIT 1")
    fun getItemById(text: String?): Long?

    @Query("SELECT * FROM RoomDataModel")
    fun getAll(): List<RoomDataModel>

    @Query("SELECT * FROM RoomDataModel WHERE text LIKE :word")
    fun findByWord(word: String): List<RoomDataModel>

    fun tryInsert(text: String?): Long =
        getItemById(text) ?: insert(RoomDataModel(text = text))

    @Query("SELECT favorite FROM RoomDataModel WHERE id = :id")
    fun getFavorite(id: Long): Boolean

}
