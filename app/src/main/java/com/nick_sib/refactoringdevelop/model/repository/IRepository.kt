package com.nick_sib.refactoringdevelop.model.repository


interface IRepository<T, R> {
    suspend fun getData(word: R): T
    suspend fun saveData(data: T)
}