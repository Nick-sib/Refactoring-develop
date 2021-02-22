package com.nick_sib.refactoringdevelop.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nick_sib.refactoringdevelop.App
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.utils.network.isOnlineFlow
import com.nick_sib.refactoringdevelop.utils.parseSearchResults
import com.nick_sib.refactoringdevelop.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val interactor: MainInteractor,
) : BaseViewModel<AppState>() {

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private val networkChanel = isOnlineFlow(App.instance)

    private val searchResult: LiveData<AppState> = _searchResult

    private val _internetState: MutableLiveData<Boolean> = MutableLiveData()
    val internetState: LiveData<Boolean>
        get() =_internetState

    fun subscribe(): LiveData<AppState> = searchResult


    init {
        launch {
            networkChanel.consumeEach {
                _internetState.value = it
            }
        }
    }

    override fun getData(word: String) {
        _searchResult.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word, _internetState.value ?: false)
        }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _searchResult.postValue(parseSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        _searchResult.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
        networkChanel.cancel()
    }
}
