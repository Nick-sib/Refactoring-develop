package com.nick_sib.repository.datasource

import com.nick_sib.model.DataModel
import com.nick_sib.repository.room.DataBase
import com.nick_sib.repository.IDataSource

class RoomDataBaseDescriptionImpl(
    private val historyDao: DataBase
): IDataSource<DataModel, Long> {

    override suspend fun getData(data: Long): DataModel =
        historyDao.findData(data)

    override suspend fun saveData(data: DataModel): DataModel {
        return historyDao.saveData(data)
    }
}