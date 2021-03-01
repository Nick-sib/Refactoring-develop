package com.nick_sib.refactoringdevelop.model.datasource

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.room.DataBase

class RoomDataBaseImpl(
    private val historyDao: DataBase
): IDataSource<List<DataModel>, String> {

    override suspend fun getData(word: String): List<DataModel> =
        historyDao.findData(word)

    override suspend fun saveData(data: List<DataModel>): Boolean {
        return historyDao.saveData(data)
    }


}