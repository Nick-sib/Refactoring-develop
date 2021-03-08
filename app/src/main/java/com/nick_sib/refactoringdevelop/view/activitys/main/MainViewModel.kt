package com.nick_sib.refactoringdevelop.view.activitys.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nick_sib.model.AppStateList
import com.nick_sib.model.DataModel
import com.nick_sib.refactoringdevelop.App
import com.nick_sib.refactoringdevelop.view.base.BaseViewModel
import com.nick_sib.utils.isOnlineFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val interactor: MainInteractor<List<DataModel>, String>,
) : BaseViewModel<AppStateList, String>() {

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private val networkChanel = isOnlineFlow(App.instance)

    private val searchResult: LiveData<AppStateList> = _searchResult

    private val _internetState: MutableLiveData<Boolean> = MutableLiveData()
    val internetState: LiveData<Boolean>
        get() =_internetState

    fun subscribe(): LiveData<AppStateList> = searchResult

    init {
        launch {
            networkChanel.consumeEach {
                _internetState.value = it
            }
        }
    }

    override fun getData(data: String) {
        _searchResult.value = AppStateList.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(data, _internetState.value ?: false)
        }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _searchResult.postValue(parseSearchResults(AppStateList.Success(interactor.getData(word, isOnline))))
    }

    override fun handleError(error: Throwable) {
        _searchResult.postValue(AppStateList.Error(error))
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
        networkChanel.cancel()
    }

    override fun setData(data: AppStateList) {
        TODO("Not yet implemented")
    }
}
