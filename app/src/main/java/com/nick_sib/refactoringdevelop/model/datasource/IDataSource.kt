package com.nick_sib.refactoringdevelop.model.datasource

interface IDataSource<T, R> {
    suspend fun getData(word: R): T
    suspend fun saveData(data: T): Boolean
}