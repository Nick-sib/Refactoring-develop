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
    fun getIdByText(text: String?): Long?

    @Query("SELECT id FROM RoomDataModel WHERE id = :id LIMIT 1")
    fun checkId(id: Long): Long?

    @Query("SELECT * FROM RoomDataModel WHERE favorite = 1 ORDER BY text")
    fun getAllFavorite(): List<RoomDataModel>

    @Query("SELECT * FROM RoomDataModel WHERE text LIKE :word")
    fun findByWord(word: String): List<RoomDataModel>

    @Query("SELECT * FROM RoomDataModel WHERE id = :id")
    fun findById(id: Long): RoomDataModel?

    fun tryInsert(id: Long, text: String?): Long =
        checkId(id) ?: insert(RoomDataModel(id = id, text = text))

    @Query("SELECT favorite FROM RoomDataModel WHERE id = :id")
    fun getFavorite(id: Long): Boolean



//    @Query("SELECT favorite FROM RoomDataModel WHERE id = :id")
//    fun setFavorite(id: Long, value: Boolean)

}
