package com.nick_sib.refactoringdevelop.model.room

import androidx.room.RoomDatabase
import com.nick_sib.refactoringdevelop.model.room.dao.CrossTabDao
import com.nick_sib.refactoringdevelop.model.room.dao.DataModelDao
import com.nick_sib.refactoringdevelop.model.room.dao.MeaningsDao

@androidx.room.Database(
    entities = [RoomDataModel::class, RoomMeanings::class, RoomCrossTable::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val dataModelDao: DataModelDao
    abstract val meaningsDao: MeaningsDao
    abstract val crossTab: CrossTabDao

    companion object {
        const val DB_NAME = "database.db"
    }
}