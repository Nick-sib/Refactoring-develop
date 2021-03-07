package com.nick_sib.refactoringdevelop.view.activitys.description

import androidx.lifecycle.LiveData
import com.nick_sib.refactoringdevelop.model.data.AppStateData
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DescriptionViewModel(
    private val repositoryLocal: IRepository<DataModel, Long>
) : BaseViewModel<AppStateData, Long>()  {

//    override val coroutineContext: CoroutineContext by lazy {
//        Dispatchers.Main + Job()
//    }

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