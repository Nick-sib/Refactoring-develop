package com.nick_sib.refactoringdevelop.view.activitys

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nick_sib.core.BaseViewModel
import com.nick_sib.core.MainInteractor
import com.nick_sib.model.AppStateList
import com.nick_sib.model.DataModel
import com.nick_sib.refactoringdevelop.App
import com.nick_sib.refactoringdevelop.App.Companion.PREFS_KEY_THEME
import com.nick_sib.utils.PreferenceDelegate
import com.nick_sib.utils.isOnlineFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class MainViewModel(
    private val interactor: MainInteractor<List<DataModel>, String>,
    sharedPrefs: SharedPreferences
) : BaseViewModel<AppStateList, String>() {

    private var isDark: Boolean by PreferenceDelegate(sharedPrefs, PREFS_KEY_THEME, false)

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private val networkChanel = isOnlineFlow(App.instance)

    private val searchResult: LiveData<AppStateList> = _searchResult

    private val _internetState: MutableLiveData<Boolean> = MutableLiveData()
    val internetState: LiveData<Boolean>
        get() =_internetState

    private val _themeState: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = isDark
        setTheme()
    }
    val themeState: LiveData<Boolean>
        get() =_themeState

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

    private fun setTheme(){
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun switchTheme(){
        isDark = !isDark
        _themeState.value = isDark
        setTheme()
    }
}
