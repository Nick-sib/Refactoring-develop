package com.nick_sib.model

sealed class AppStateData {
    data class Success(val data: DataModel) : AppStateData()
    data class Error(val error: Throwable) : AppStateData()
    data class Loading(val progress: Int?) : AppStateData()
}