package com.nick_sib.refactoringdevelop.view.activitys.description

import androidx.lifecycle.LiveData
import com.nick_sib.model.AppStateData
import com.nick_sib.model.DataModel
import com.nick_sib.refactoringdevelop.view.base.BaseViewModel
import com.nick_sib.repository.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DescriptionViewModel(
    private val repositoryLocal: IRepository<DataModel, Long>
) : BaseViewModel<AppStateData, Long>()  {

    private val searchResult: LiveData<AppStateData> = _searchResult

    fun subscribe(): LiveData<AppStateData> = searchResult

    override fun getData(data: Long) {
        _searchResult.value = AppStateData.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            loadData(data)
        }
    }
    private suspend fun loadData(id: Long) = withContext(Dispatchers.IO) {
        _searchResult.postValue(AppStateData.Success(repositoryLocal.getData(id)))
    }

    private suspend fun startInteractor(data: DataModel) = withContext(Dispatchers.IO) {
        _searchResult.postValue(AppStateData.Success(repositoryLocal.setData(data)))
    }

    override fun handleError(error: Throwable) {
        _searchResult.postValue(AppStateData.Error(error))
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    override fun setData(data: AppStateData) {
        if (data is AppStateData.Success)
            viewModelCoroutineScope.launch {
                startInteractor(data.data)
            }
    }

}