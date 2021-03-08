package com.nick_sib.refactoringdevelop.view.activitys.history

import androidx.lifecycle.LiveData
import com.nick_sib.model.AppStateList
import com.nick_sib.model.DataModel

import com.nick_sib.core.BaseViewModel
import com.nick_sib.repository.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
    private val repositoryLocal: IRepository<List<DataModel>, String>
) : BaseViewModel<AppStateList, String>() {

    private val searchResult: LiveData<AppStateList> = _searchResult

    fun subscribe(): LiveData<AppStateList> = searchResult

    init {
        getData("*")
    }

    override fun getData(data: String) {
        _searchResult.value = AppStateList.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            loadData(data)
        }
    }

    private suspend fun loadData(data: String) = withContext(Dispatchers.IO) {
        _searchResult.postValue(AppStateList.Success(repositoryLocal.getData(data)))
    }

    override fun setData(data: AppStateList) {
        TODO("Not yet implemented")
    }

    override fun handleError(error: Throwable) {
        _searchResult.postValue(AppStateList.Error(error))
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}