package com.nick_sib.refactoringdevelop.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nick_sib.refactoringdevelop.model.room.RoomCrossTable

@Dao
interface CrossTabDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RoomCrossTable)

    @Query("DELETE FROM RoomCrossTable")
    fun clearCrossTable()

    @Query("SELECT meaningsId FROM RoomCrossTable WHERE datamodelId = :idDataModel")
    fun getMeaningsId(idDataModel: Long): List<Long>

//    @Query(
//        "SELECT RoomMeanings.translation " +
//                "FROM  RoomCrossTab INNER JOIN RoomMeanings ON RoomCrossTab.meaningsId = RoomMeanings.id " +
//                "WHERE RoomCrossTab.dataModelId = :idDataModel"
//    )
//    fun getSpecialtyByEmployeeId(idDataModel: Long): List<String>
}