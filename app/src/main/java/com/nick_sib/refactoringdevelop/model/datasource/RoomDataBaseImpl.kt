package com.nick_sib.refactoringdevelop.model.datasource

import com.nick_sib.refactoringdevelop.model.data.DataModel

class RoomDataBaseImpl: IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> =
        emptyList()
}