package com.nick_sib.refactoringdevelop.model.datasource

interface IDataSource<T> {
    suspend fun getData(word: String): T
}