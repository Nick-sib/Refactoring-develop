package com.nick_sib.refactoringdevelop.model.datasource

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.room.DataBase

class RoomDataBaseDescriptionImpl(
    private val historyDao: DataBase
): IDataSource<DataModel, Long> {

    override suspend fun getData(data: Long): DataModel =
        historyDao.findData(data)

    override suspend fun saveData(data: DataModel): DataModel {
        return historyDao.saveData(data)
    }
}