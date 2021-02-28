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

    @Query("SELECT * FROM RoomDataModel")
    fun getAll(): List<RoomDataModel>

}