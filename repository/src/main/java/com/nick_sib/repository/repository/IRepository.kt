package com.nick_sib.repository.repository

interface IRepository<T, R> {
    suspend fun getData(request: R): T
    suspend fun setData(data: T): T
}