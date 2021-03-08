package com.nick_sib.repository

interface IDataSource<T, R> {
    suspend fun getData(word: R): T
    suspend fun saveData(data: T): T
}