package com.nick_sib.repository.datasource

import com.nick_sib.model.DataModel
import com.nick_sib.repository.room.DataBase
import com.nick_sib.repository.IDataSource

class RoomDataBaseImpl(
    private val historyDao: DataBase
): IDataSource<List<DataModel>, String> {

    override suspend fun getData(word: String): List<DataModel> =
        historyDao.findData(word)

    override suspend fun saveData(data: List<DataModel>): List<DataModel> {
        return historyDao.saveData(data)
    }


}