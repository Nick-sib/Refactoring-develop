package com.nick_sib.refactoringdevelop.model.repository


interface IRepository<T> {
    suspend fun getData(word: String): T
}