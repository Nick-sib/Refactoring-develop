package com.nick_sib.refactoringdevelop.model.data

sealed class AppStateData {
    data class Success(val data: DataModel) : AppStateData()
    data class Error(val error: Throwable) : AppStateData()
    data class Loading(val progress: Int?) : AppStateData()
}