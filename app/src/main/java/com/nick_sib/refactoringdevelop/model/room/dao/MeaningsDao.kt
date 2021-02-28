package com.nick_sib.refactoringdevelop.model.room.dao

import androidx.room.*
import com.nick_sib.refactoringdevelop.model.room.RoomMeanings

@Dao
interface MeaningsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RoomMeanings)

    @Update
    fun update(data: RoomMeanings)

    @Delete
    fun delete(data: RoomMeanings)

    @Query("SELECT * FROM RoomMeanings")
    fun getAll(): List<RoomMeanings>
}