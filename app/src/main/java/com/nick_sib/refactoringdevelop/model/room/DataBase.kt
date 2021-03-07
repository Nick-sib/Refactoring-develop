package com.nick_sib.refactoringdevelop.model.room

import androidx.room.RoomDatabase
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.data.Meanings
import com.nick_sib.refactoringdevelop.model.data.Translation
import com.nick_sib.refactoringdevelop.model.room.dao.DataModelDao
import com.nick_sib.refactoringdevelop.model.room.dao.MeaningsDao

@androidx.room.Database(
    entities = [RoomDataModel::class, RoomMeanings::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {
    abstract val dataModelDao: DataModelDao
    abstract val meaningsDao: MeaningsDao

    fun saveData(data: List<DataModel>): List<DataModel> {
        return data.map {
            dataModelDao.tryInsert(it.id, it.text)
            meaningsDao.insertOrUpdate(it.id, it.meanings)
            it.isFavorite = dataModelDao.getFavorite(it.id)
            it
        }
    }

    fun saveData(data: DataModel): DataModel {
        val dd = RoomDataModel(data.id, data.isFavorite, data.text)
        dataModelDao.update(dd)
        return data
    }

    fun findData(word: String): List<DataModel> = (if (word == "*")
        dataModelDao.getAllFavorite() else dataModelDao.findByWord("%$word%")).map {
            val meanings = it.id.let {  id ->
                meaningsDao.getItemByDataId(id)
            }.map { res ->
                Meanings(Translation(res.translation), res.imageUrl)
            }
            DataModel(it.id, it.text, meanings).apply {
                isFavorite = it.favorite
            }
        }

    fun findData(id: Long): DataModel =
        dataModelDao.findById(id)?.let {
            val meanings = it.id.let {  id ->
                meaningsDao.getItemByDataId(id)
            }.map { res ->
                Meanings(Translation(res.translation), res.imageUrl)
            }
            DataModel(it.id, it.text, meanings, it.favorite)
        } ?: DataModel(-1,null, null)
}