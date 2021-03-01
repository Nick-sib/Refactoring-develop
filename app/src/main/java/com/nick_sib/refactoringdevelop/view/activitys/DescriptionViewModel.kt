package com.nick_sib.refactoringdevelop.view.activitys

import com.nick_sib.refactoringdevelop.model.data.AppStateData
import com.nick_sib.refactoringdevelop.model.data.AppStateList
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.view.main.MainInteractor
import com.nick_sib.refactoringdevelop.viewmodel.BaseViewModel

class DescriptionViewModel(
    private val repositoryLocal: IRepository<AppStateData, String>
) : BaseViewModel<AppStateData>()  {


    override fun getData(word: String) {
        TODO("Not yet implemented")
    }

    override fun handleError(error: Throwable) {
        TODO("Not yet implemented")
    }
}