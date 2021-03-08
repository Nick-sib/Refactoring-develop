package com.nick_sib.refactoringdevelop.view.activitys.main

import com.nick_sib.model.AppStateList
import com.nick_sib.model.DataModel
import com.nick_sib.model.Meanings

fun parseSearchResults(state: AppStateList): AppStateList {
    val newSearchResults = arrayListOf<DataModel>()
    when (state) {
        is AppStateList.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
        else -> arrayListOf<DataModel>()
    }
    return AppStateList.Success(newSearchResults)
}

private fun parseResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && dataModel.meanings.isNotEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings) {
            if (meaning.text.isNotBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.id, dataModel.text, newMeanings).apply {
                isFavorite = dataModel.isFavorite
            })
        }
    }
}