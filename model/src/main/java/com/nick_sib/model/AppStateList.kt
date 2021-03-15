package com.nick_sib.model

sealed class AppStateList {
    data class Success(val data: List<DataModel>) : AppStateList()
    data class Error(val error: Throwable) : AppStateList()
    data class Loading(val progress: Int?) : AppStateList()
}